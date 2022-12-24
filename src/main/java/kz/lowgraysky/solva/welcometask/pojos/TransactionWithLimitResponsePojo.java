package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionWithLimitResponsePojo implements BasePojo {

    @JsonProperty("account_from")
    private Long accountFrom;

    @JsonProperty("account_to")
    private Long accountTo;

    @JsonProperty("currency_shortname")
    private String currencyShortName;

    @JsonProperty("sum")
    private BigDecimal sum;

    @JsonProperty("expense_category")
    private ExpenseCategory expenseCategory;

    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime dateTime;

    @JsonProperty("limit_sum")
    private BigDecimal limitSum;

    @JsonProperty("limit_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime limitDateTime;

    @JsonProperty("limit_currency_shortname")
    private String limitCurrencyShortName;
}

