package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionPojo implements Serializable {

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
    private BigDecimal sum;

    @NonNull
    @NotBlank
    @JsonProperty("expense_category")
    private String expenseCategory;

    @NonNull
    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssX")
    private LocalDateTime dateTime;
}
