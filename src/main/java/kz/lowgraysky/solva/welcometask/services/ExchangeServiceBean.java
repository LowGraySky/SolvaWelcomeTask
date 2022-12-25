package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.configuration.ConfigProperties;
import kz.lowgraysky.solva.welcometask.entities.ExchangeRate;
import kz.lowgraysky.solva.welcometask.exceptions.EnrichmentFromRemoteException;
import kz.lowgraysky.solva.welcometask.exceptions.MissingDataException;
import kz.lowgraysky.solva.welcometask.pojos.ExchangeRateResponsePojo;
import kz.lowgraysky.solva.welcometask.repositories.ExchangeRateRepository;
import kz.lowgraysky.solva.welcometask.utils.BeanHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeServiceBean extends BeanHelper implements ExchangeService {

    private final ConfigProperties configProperties;
    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeServiceBean(ConfigProperties configProperties,
                               RestTemplate restTemplate,
                               ExchangeRateRepository exchangeRateRepository) {
        this.configProperties = configProperties;
        this.restTemplate = restTemplate;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate save(ExchangeRate inst) {
        ExchangeRate rate = exchangeRateRepository.save(inst);
        logger.info(String.format("Save %s entity: %s", ExchangeRate.class, rate));
        return rate;
    }

    @Override
    public ExchangeRate getById(long id) {
        return null;
    }

    @Override
    public ExchangeRateResponsePojo requestTimeSeries(String symbol, String interval) {
        StringBuilder sb = new StringBuilder(configProperties.EXCHANGE_SERVICE_URL());
        sb.append("/time_series?");
        sb.append(String.format("symbol=%s&", symbol));
        sb.append(String.format("interval=%s&", interval));
        sb.append(String.format("apikey=%s", configProperties.EXCHANGE_SERVICE_API_KEY()));
        ExchangeRateResponsePojo result = this.restTemplate
                .getForObject(sb.toString(), ExchangeRateResponsePojo.class);
        logger.info(String.format("Get exchange rates from %s. Result: %s", sb, result));
        if(result != null && result.getStatus() != null && !result.getStatus().equals("ok")){
            throw new EnrichmentFromRemoteException(
                    String.format("Error occurred in processing request to %s. Message: %s", sb, result.getMessage()));
        }
        return result;
    }

    @Override
    public void enrichExchangeRatesFromRemote(String symbol) {
        ExchangeRateResponsePojo enrichData = requestTimeSeries(symbol, "1day");
        List<ExchangeRate> rates = exchangeRatePojoToEntity(enrichData);
        exchangeRateRepository.saveAll(rates);
        logger.info(String.format("Save %s entities: %s", ExchangeRate.class, rates.toString()));
    }

    @Override
    public BigDecimal getActualClosePriceForExchangeRate(String symbol, LocalDate date) {
        ExchangeRate exchangeRate = exchangeRateRepository.getByDateTimeAndSymbol(symbol, date);
        ExchangeRate previousDayExchangeRate = exchangeRateRepository
                .getByDateTimeAndSymbol(symbol, date.minusDays(1));
        if(exchangeRate != null && exchangeRate.getClose() != null){
            return exchangeRate.getClose();
        }
        if(previousDayExchangeRate != null && previousDayExchangeRate.getClose() != null){
            return previousDayExchangeRate.getClose();
        }
        try{
            enrichExchangeRatesFromRemote(symbol);
            exchangeRate = exchangeRateRepository.getByDateTimeAndSymbol(symbol, date);
            previousDayExchangeRate = exchangeRateRepository
                    .getByDateTimeAndSymbol(symbol, date.minusDays(1));
            if(exchangeRate != null && exchangeRate.getClose() != null){
                return exchangeRate.getClose();
            }
            if(previousDayExchangeRate != null && previousDayExchangeRate.getClose() != null){
                return previousDayExchangeRate.getClose();
            }
        }catch (EnrichmentFromRemoteException exception){
            exception.printStackTrace();
        }
        logger.info(String.format(
                "No actual close exchange rate for symbol: %s on date: %s and %s." +
                        " Trying get last available exchange rate from database.",
                symbol, date, date.minusDays(1)));
        ExchangeRate lastAvailable = exchangeRateRepository.getBySymbolAndLastDate(symbol);
        if(lastAvailable != null){
            logger.info(String.format("Last available exchange rate available on date: %s",
                    lastAvailable.getDateTime()));
        }
        if(lastAvailable != null && lastAvailable.getClose() != null){
            return lastAvailable.getClose();
        }
        throw new MissingDataException(
                String.format(
                        "Cannot get exchange rate for symbol: %s. " +
                                "Cannot get from database and remote enrichment service.", symbol));
    }

    public List<ExchangeRate> exchangeRatePojoToEntity(ExchangeRateResponsePojo pojo){
        List<ExchangeRate> rates = new ArrayList<>(pojo.getValues().size());
        for(ExchangeRateResponsePojo.Values value : pojo.getValues()){
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setSymbol(pojo.getMeta().getSymbol());
            exchangeRate.setOpen(value.getOpen());
            exchangeRate.setClose(value.getClose());
            exchangeRate.setHigh(value.getHigh());
            exchangeRate.setLow(value.getLow());
            exchangeRate.setDateTime(value.getDatetime());
            rates.add(exchangeRate);
        }
        return rates;
    }
}
