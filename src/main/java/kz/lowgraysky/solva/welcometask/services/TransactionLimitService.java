package kz.lowgraysky.solva.welcometask.services;


import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public interface TransactionLimitService extends CommonService<TransactionLimit>{

    void insert(TransactionLimit inst);

    TransactionLimit setNewLimit(BigDecimal amount, ExpenseCategory category, Long address);

    TransactionLimit getByExpenseCategoryAndMaxStandBy(ExpenseCategory category, Long address, Month month);

    List<TransactionLimit> getAllByBankAccountAddress(Long address);
}
