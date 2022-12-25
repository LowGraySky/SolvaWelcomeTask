package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.repositories.TransactionLimitInsertRepository;
import kz.lowgraysky.solva.welcometask.repositories.TransactionLimitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TransactionLimitServiceBean implements TransactionLimitService{


    private final TransactionLimitInsertRepository transactionLimitInsertRepository;
    private final TransactionLimitRepository transactionLimitRepository;

    public TransactionLimitServiceBean(TransactionLimitInsertRepository transactionLimitInsertRepository, TransactionLimitRepository transactionLimitRepository) {
        this.transactionLimitInsertRepository = transactionLimitInsertRepository;
        this.transactionLimitRepository = transactionLimitRepository;
    }

    @Override
    public void insert(TransactionLimit inst) {
        transactionLimitInsertRepository.insert(inst);
    }

    @Override
    public TransactionLimit setNewLimit(BigDecimal amount, ExpenseCategory category) {
        TransactionLimit limit = getByExpenseCategoryAndMaxStandBy(category);
        TransactionLimit newLimit = new TransactionLimit(
                amount,
                limit.getExpenseCategory(),
                ZonedDateTime.now(),
                limit.getCurrency()
        );
        insert(newLimit);
        return newLimit;
    }

    @Override
    public TransactionLimit getByExpenseCategoryAndMaxStandBy(ExpenseCategory category) {
        return transactionLimitRepository.getByExpenseCategoryAndMaxStandBy(category);
    }

    @Override
    public List<TransactionLimit> getAllLimits(){
        return (List<TransactionLimit>) transactionLimitRepository.findAll();
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
