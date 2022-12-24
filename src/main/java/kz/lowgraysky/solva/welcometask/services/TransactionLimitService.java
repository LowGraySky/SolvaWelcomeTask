package kz.lowgraysky.solva.welcometask.services;


import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;

import java.math.BigDecimal;

public interface TransactionLimitService extends CommonService<TransactionLimit>{

    TransactionLimit setNewLimit(BigDecimal amount, ExpenseCategory category);

    TransactionLimit getByExpenseCategory(ExpenseCategory category);
}
