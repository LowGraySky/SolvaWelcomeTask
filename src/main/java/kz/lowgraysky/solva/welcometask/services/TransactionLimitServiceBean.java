package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.repositories.TransactionLimitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class TransactionLimitServiceBean implements TransactionLimitService{

    private final TransactionLimitRepository transactionLimitRepository;

    public TransactionLimitServiceBean(TransactionLimitRepository transactionLimitRepository) {
        this.transactionLimitRepository = transactionLimitRepository;
    }

    @Override
    public TransactionLimit setNewLimit(BigDecimal amount, ExpenseCategory category) {
        TransactionLimit limit = getByExpenseCategory(category);
        limit.setAmount(amount);
        limit.setStandByDate(ZonedDateTime.now());
        return save(limit);
    }

    @Override
    public TransactionLimit getByExpenseCategory(ExpenseCategory category) {
        return transactionLimitRepository.getByExpenseCategory(category);
    }

    @Override
    public TransactionLimit save(TransactionLimit inst) {
        return transactionLimitRepository.save(inst);
    }

    @Override
    public TransactionLimit getById(long id) {
        return null;
    }
}
