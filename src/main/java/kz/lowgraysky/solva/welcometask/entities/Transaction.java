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
@Table(name = "TRANSACTION", schema = "public")
public class Transaction extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ACCOUNT_FROM_ID", nullable = false)
    private BankAccount accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ACCOUNT_TO_ID", nullable = false)
    private BankAccount accountTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID", nullable = false)
    private Currency currency;

    @Column(name = "SUM", nullable = false)
    private BigDecimal sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXPENSE_CATEGORY", nullable = false)
    private ExpenseCategory expenseCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TIME", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime dateTime;

    @Column(name = "LIMIT_EXCEDEED")
    private boolean limitExceeded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_LIMIT_ID", nullable = true)
    private TransactionLimit transactionLimit;

    public Transaction(
            BankAccount accountFrom,
            BankAccount accountTo,
            Currency currency,
            BigDecimal sum,
            ExpenseCategory expenseCategory,
            ZonedDateTime dateTime){
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currency = currency;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.dateTime = dateTime;
    }
}
