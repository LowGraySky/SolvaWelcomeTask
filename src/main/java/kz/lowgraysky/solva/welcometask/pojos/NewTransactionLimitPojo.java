package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class NewTransactionLimitPojo implements BasePojo{

    @NonNull
    @JsonProperty("amount")
    private BigDecimal amount;

    @NonNull
    @NotBlank
    @JsonProperty("category")
    private String expenseCategory;
}
