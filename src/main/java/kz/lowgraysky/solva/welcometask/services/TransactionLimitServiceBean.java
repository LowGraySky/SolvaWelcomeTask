package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.repositories.TransactionLimitInsertRepository;
import kz.lowgraysky.solva.welcometask.repositories.TransactionLimitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TransactionLimitServiceBean implements TransactionLimitService{

    private final TransactionLimitInsertRepository transactionLimitInsertRepository;
    private final TransactionLimitRepository transactionLimitRepository;
    private final BankAccountService bankAccountService;
    private final CurrencyServiceBean currencyService;

    public TransactionLimitServiceBean(
            TransactionLimitInsertRepository transactionLimitInsertRepository,
            TransactionLimitRepository transactionLimitRepository,
            BankAccountService bankAccountService,
            CurrencyServiceBean currencyService) {
        this.transactionLimitInsertRepository = transactionLimitInsertRepository;
        this.transactionLimitRepository = transactionLimitRepository;
        this.bankAccountService = bankAccountService;
        this.currencyService = currencyService;
    }

    @Override
    public void insert(TransactionLimit inst) {
        transactionLimitInsertRepository.insert(inst);
    }

    @Override
    public TransactionLimit setNewLimit(BigDecimal amount, ExpenseCategory category, Long address) {
        TransactionLimit limit = getByExpenseCategoryAndMaxStandBy(category, address, ZonedDateTime.now().getMonth());
        BigDecimal newAvailableAmount;
        if(limit == null){
            newAvailableAmount = amount;
        }else{
            if(amount.compareTo(limit.getAmount()) > 0){
                newAvailableAmount = amount.subtract(limit.getAmount()).add(limit.getAvailableAmount());
            }else{
                newAvailableAmount = amount.subtract(limit.getAvailableAmount());
            }
        }
        TransactionLimit newLimit = new TransactionLimit(
                amount,
                category,
                ZonedDateTime.now(),
                currencyService.getByShortName("USD"),
                bankAccountService.getByAddress(address),
                newAvailableAmount
        );
        insert(newLimit);
        return newLimit;
    }

    @Override
    public TransactionLimit getByExpenseCategoryAndMaxStandBy(ExpenseCategory category, Long address, Month month) {
        return transactionLimitRepository.getByExpenseCategoryAndMaxStandBy(category, address, month);
    }

    @Override
    public List<TransactionLimit> getAllByBankAccountAddress(Long address) {
        return transactionLimitRepository.getAllByBankAccountAddress(address);
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
