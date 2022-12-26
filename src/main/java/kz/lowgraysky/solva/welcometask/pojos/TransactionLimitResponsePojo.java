package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Month;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLimitResponsePojo implements BasePojo{

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("category")
    private ExpenseCategory expenseCategory;

    @JsonProperty("stand_by")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime standByDate;

    @JsonProperty("limit_currency_shortname")
    private String currencyShortName;

    @JsonProperty("address")
    private Long accountAddress;

    @JsonProperty("available_amount")
    private BigDecimal availableAmount;

    @JsonProperty("month")
    private Month month;
}
