package kz.lowgraysky.solva.welcometask.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTION_LIMIT", schema = "public")
public class TransactionLimit extends BaseEntity {

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXPENSE_CATEGORY", nullable = false, unique = true)
    private ExpenseCategory expenseCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STAND_BY", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime standByDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID", nullable = false)
    private Currency currency;

    public TransactionLimit(BigDecimal amount, ExpenseCategory category, ZonedDateTime standBy, Currency currency){
        this.amount = amount;
        this.expenseCategory = category;
        this.standByDate = standBy;
        this.currency = currency;
    }
}
