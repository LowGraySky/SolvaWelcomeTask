package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ExchangeRateResponsePojo implements BasePojo{

    private Meta meta;
    private List<Values> values;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @Getter
    @Setter
    public static class Meta{

        @JsonProperty("symbol")
        private String symbol;

        @JsonProperty("interval")
        private String interval;

        @JsonProperty("currency_base")
        private String currencyBase;

        @JsonProperty("currency_quote")
        private String currencyQuote;

        @JsonProperty("type")
        private String type;
    }

    @Getter
    @Setter
    public static class Values{

        @JsonProperty("datetime")
        private LocalDate datetime;

        @JsonProperty("open")
        private BigDecimal open;

        @JsonProperty("high")
        private BigDecimal high;

        @JsonProperty("low")
        private BigDecimal low;

        @JsonProperty("close")
        private BigDecimal close;
    }
}
