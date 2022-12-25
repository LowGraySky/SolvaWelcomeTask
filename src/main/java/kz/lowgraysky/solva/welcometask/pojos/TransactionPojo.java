package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.validator.CheckExpenseCategory;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class TransactionPojo implements BasePojo {

    @NonNull
    @DecimalMax(value = "9999999999", message = "Account address must be not bigger than 9_999_999_999")
    @JsonProperty("account_from")
    private Long accountFrom;

    @NonNull
    @DecimalMax(value = "9999999999", message = "Account address must be not bigger than 9_999_999_999.")
    @JsonProperty("account_to")
    private Long accountTo;

    @NonNull
    @Size(max = 3, message = "Currency shortname must be not longer than {max} and uppercase.")
    @JsonProperty("currency_shortname")
    private String currencyShortName;

    @NonNull
    @JsonProperty("sum")
    private Double sum;

    @NonNull 
    @CheckExpenseCategory(ExpenseCategory.PRODUCT)
    @JsonProperty("expense_category")
    private String expenseCategory;

    @NonNull
    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime dateTime;
}
