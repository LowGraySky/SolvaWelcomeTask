package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class TransactionPojo implements BasePojo {

    @NonNull
    @JsonProperty("account_from")
    private Long accountFrom;

    @NonNull
    @JsonProperty("account_to")
    private Long accountTo;

    @NonNull
    @NotBlank
    @JsonProperty("currency_shortname")
    private String currencyShortName;

    @NonNull
    @JsonProperty("sum")
    private Double sum;

    @NonNull
    @NotBlank
    @JsonProperty("expense_category")
    private String expenseCategory;

    @NonNull
    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime dateTime;
}
