package kz.lowgraysky.solva.welcometask.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ExchangeRateResponsePojo {

    private Meta meta;
    private List<Values> values;
    private Status status;
    private String message;

    @Getter
    @Setter
    public class Meta{
        private String symbol;
        private String interval;
        private String currency_base;
        private String currency_quote;
        private String type;
    }

    @Getter
    @Setter
    public class Values{
        private LocalDate datetime;
        private BigDecimal open;
        private BigDecimal high;
        private BigDecimal low;
        private BigDecimal close;
    }

    @Getter
    @Setter
    public class Status{
        private String status;
    }
}
