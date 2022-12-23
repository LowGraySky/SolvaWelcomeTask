package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.*;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    public TransactionLimit(BigDecimal amount, ExpenseCategory category){
        this.amount = amount;
        this.expenseCategory = category;
    }
}
