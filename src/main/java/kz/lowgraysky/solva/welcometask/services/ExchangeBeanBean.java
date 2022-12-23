package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.configuration.ConfigProperties;
import kz.lowgraysky.solva.welcometask.entities.ExchangeRate;
import kz.lowgraysky.solva.welcometask.pojos.ExchangeRateResponsePojo;
import kz.lowgraysky.solva.welcometask.repositories.ExchangeRateRepository;
import kz.lowgraysky.solva.welcometask.utils.BeanHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeBeanBean extends BeanHelper implements ExchangeService {

    private final ConfigProperties configProperties;
    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeBeanBean(ConfigProperties configProperties,
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
        logger.info(String.format("Get exchange rates from %s. Result: %s"), sb, result);
        if(result != null && result.getStatus() != null && !result.getStatus().equals("ok")){
            throw new RuntimeException(
                    String.format("Error occurred in processing request to %s. Message: %s", sb, result.getMessage()));
        }
        return result;
    }

    @Override
    public void enrichExchangeRatesFromRemote(String symbol) {
        ExchangeRateResponsePojo enrichData = requestTimeSeries(symbol, LocalDateTime.now().toString());
        List<ExchangeRate> rates = exchangeRatePojoToEntity(enrichData);
        exchangeRateRepository.saveAll(rates);
        logger.info(String.format("Save %s entitys: %s", ExchangeRate.class, rates.toString()));
    }

    @Override
    public BigDecimal getActualClosePriceForExchangeRate(String symbol, LocalDateTime dateTime) {
        ExchangeRate exchangeRate = exchangeRateRepository.getByDateTimeAndSymbol(symbol, dateTime);
        ExchangeRate previousDayExchangeRate = exchangeRateRepository
                .getByDateTimeAndSymbol(symbol, dateTime.minusDays(1));
        if(exchangeRate == null){
            if(previousDayExchangeRate == null){
                enrichExchangeRatesFromRemote(symbol);
                getActualClosePriceForExchangeRate(symbol, dateTime);
            }
        }else{
            if(exchangeRate.getClose() == null){
                if(previousDayExchangeRate.getClose() == null) {
                    enrichExchangeRatesFromRemote(symbol);
                    getActualClosePriceForExchangeRate(symbol, dateTime);
                }else{
                    return previousDayExchangeRate.getClose();
                }
            }else{
                return exchangeRate.getClose();
            }
        }
        return null;
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
            exchangeRate.setDateTime(LocalDateTime.of(value.getDatetime(), LocalTime.MIDNIGHT));
            rates.add(exchangeRate);
        }
        return rates;
    }
}
