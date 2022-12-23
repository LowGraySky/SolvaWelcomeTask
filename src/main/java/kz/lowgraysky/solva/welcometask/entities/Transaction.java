package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.*;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTION", schema = "public")
public class Transaction extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_FROM")
    private BankAccount accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TO")
    private BankAccount accountTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @Column(name = "SUM", nullable = false)
    private BigDecimal sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXPENSE_CATEGORY", nullable = false)
    private ExpenseCategory expenseCategory;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    @Column(name = "LIMIT_EXCEDEED")
    private boolean limitExceeded = false;

    @Column(name = "LIMIT_SUM", nullable = true)
    private BigDecimal limitSum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LIMIT_DATE_TIME", nullable = true)
    private LocalDateTime limitDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIMIT_CURRENCY_ID")
    private Currency limitCurrency;

    public Transaction(
            BankAccount accountFrom,
            BankAccount accountTo,
            Currency currency,
            BigDecimal sum,
            ExpenseCategory expenseCategory,
            LocalDateTime dateTime){
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currency = currency;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.dateTime = dateTime;
    }
}
