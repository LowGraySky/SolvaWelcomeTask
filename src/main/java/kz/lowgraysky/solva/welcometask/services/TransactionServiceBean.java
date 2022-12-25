package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.configuration.ConfigProperties;
import kz.lowgraysky.solva.welcometask.entities.*;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.pojos.TransactionPojo;
import kz.lowgraysky.solva.welcometask.repositories.*;
import kz.lowgraysky.solva.welcometask.utils.BeanHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceBean extends BeanHelper implements TransactionsService{

    private final TransactionRepository transactionRepository;
    private final BankAccountServiceBean bankAccountService;
    private final CurrencyServiceBean currencyService;
    private final CurrencyRepository currencyRepository;
    private final TransactionInsertRepository transactionInsertRepository;
    private final TransactionLimitServiceBean transactionLimitService;
    private final ExchangeServiceBean exchangeService;
    private final ConfigProperties configProperties;

    public TransactionServiceBean(TransactionRepository transactionRepository,
                                  BankAccountServiceBean bankAccountService,
                                  CurrencyServiceBean currencyService,
                                  CurrencyRepository currencyRepository,
                                  TransactionInsertRepository transactionInsertRepository,
                                  TransactionLimitServiceBean transactionLimitService,
                                  ExchangeServiceBean exchangeService,
                                  ConfigProperties configProperties) {
        this.transactionRepository = transactionRepository;
        this.bankAccountService = bankAccountService;
        this.currencyService = currencyService;
        this.currencyRepository = currencyRepository;
        this.transactionInsertRepository = transactionInsertRepository;
        this.transactionLimitService = transactionLimitService;
        this.exchangeService = exchangeService;
        this.configProperties = configProperties;
    }

    @Override
    public Transaction save(Transaction inst) {
        return transactionRepository.save(inst);
    }

    @Override
    public Transaction getById(long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getAllTransactionWithTimeLimitExceed(Long address) {
        return transactionRepository.getAllTransactionsWithTimeLimitExceed(address);
    }

    @Override
    public void checkOnTransactionLimit(Transaction inst) {
        TransactionLimit limit = transactionLimitService.getByExpenseCategoryAndMaxStandBy(
                inst.getExpenseCategory(),
                inst.getAccountFrom().getAddress(),
                inst.getDateTime().getMonth()
        );
        if(limit == null){
            limit = transactionLimitService.setNewLimit(
                    inst.getExpenseCategory() == ExpenseCategory.PRODUCT ?
                            configProperties.PRODUCT_MONTH_LIMIT() : configProperties.SERVICE_MONTH_LIMIT(),
                    inst.getExpenseCategory(),
                    inst.getAccountFrom().getAddress()
            );
        }
        BigDecimal transactionAmountWithCurrency;
        if(!inst.getCurrency().getShortName().equals("USD")) {
            BigDecimal actualCurrencyExchangeRate = exchangeService.getActualClosePriceForExchangeRate(
                    String.format("USD/%s", inst.getCurrency().getShortName()),
                    LocalDate.now()
            );
            transactionAmountWithCurrency =
                    inst.getSum().divide(actualCurrencyExchangeRate, 2, RoundingMode.HALF_UP);
        }else{
            transactionAmountWithCurrency = inst.getSum();
        }

        if(transactionAmountWithCurrency.compareTo(limit.getAvailableAmount()) > 0){
            inst.setLimitExceeded(true);
            inst.setTransactionLimit(limit);
        }
        limit.setAvailableAmount(limit.getAvailableAmount().subtract(transactionAmountWithCurrency));
        transactionLimitService.save(limit);
    }

    public Transaction insertTransactionWithCheckOnLimit(TransactionPojo pojo){
        Transaction transaction = transactionPojoToEntity(pojo);
        checkOnTransactionLimit(transaction);
        insert(transaction);
        return transaction;
    }

    @Override
    public void insert(Transaction inst) {
        transactionInsertRepository.insert(inst);
    }

    public Transaction transactionPojoToEntity(TransactionPojo pojo){
        BankAccount bankAccountFrom = bankAccountService.getByAddress(pojo.getAccountFrom());
        BankAccount bankAccountTo = bankAccountService.getByAddress(pojo.getAccountTo());
        if(bankAccountFrom == null){
            bankAccountFrom = bankAccountService.createAccount(pojo.getAccountFrom(), BankAccountOwnerType.CLIENT);
        }
        if(bankAccountTo == null){
            bankAccountTo = bankAccountService.createAccount(pojo.getAccountTo(), BankAccountOwnerType.CONTR_AGENT);
        }

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(bankAccountFrom);
        transaction.setAccountTo(bankAccountTo);
        transaction.setExpenseCategory(ExpenseCategory.fromId(pojo.getExpenseCategory()));
        Currency currency = currencyService.getByShortName(pojo.getCurrencyShortName());
        if(currency == null){
            currency = new Currency();
            currency.setShortName(pojo.getCurrencyShortName());
            currencyRepository.save(currency);
        }
        transaction.setCurrency(currency);
        transaction.setDateTime(pojo.getDateTime());
        transaction.setSum(BigDecimal.valueOf(pojo.getSum()));
        return transaction;
    }
}
