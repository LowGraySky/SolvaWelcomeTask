package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.ExchangeRate;
import kz.lowgraysky.solva.welcometask.pojos.ExchangeRateResponsePojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ExchangeService extends CommonService<ExchangeRate>{

    ExchangeRateResponsePojo requestTimeSeries(String symbol, String interval);

    void enrichExchangeRatesFromRemote(String symbol);

    BigDecimal getActualClosePriceForExchangeRate(String symbol, LocalDateTime dateTime);
}
