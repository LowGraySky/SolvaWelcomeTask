package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
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

    @NonNull
    @DecimalMax(value = "9999999999", message = "Account address must be not bigger than 9_999_999_999.")
    @JsonProperty("address")
    private Long accountAddress;
}
