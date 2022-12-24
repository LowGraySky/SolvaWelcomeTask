package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.*;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.pojos.TransactionPojo;
import kz.lowgraysky.solva.welcometask.repositories.*;
import kz.lowgraysky.solva.welcometask.utils.BeanHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceBean extends BeanHelper implements TransactionsService{

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    private final CurrencyServiceBean currencyService;
    private final CurrencyRepository currencyRepository;
    private final TransactionInsertRepository transactionInsertRepository;
    private final TransactionLimitServiceBean transactionLimitService;
    private final ExchangeServiceBean exchangeService;

    public TransactionServiceBean(TransactionRepository transactionRepository,
                                  BankAccountRepository bankAccountRepository,
                                  BankAccountOwnerRepository bankAccountOwnerRepository,
                                  CurrencyServiceBean currencyService,
                                  CurrencyRepository currencyRepository,
                                  TransactionInsertRepository transactionInsertRepository,
                                  TransactionLimitServiceBean transactionLimitService,
                                  ExchangeServiceBean exchangeService) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountOwnerRepository = bankAccountOwnerRepository;
        this.currencyService = currencyService;
        this.currencyRepository = currencyRepository;
        this.transactionInsertRepository = transactionInsertRepository;
        this.transactionLimitService = transactionLimitService;
        this.exchangeService = exchangeService;
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
    public List<Transaction> getAllTransactionWithTimeLimitExceed() {
        return transactionRepository.getAllTransactionsWithTimeLimitExceed();
    }

    @Override
    public void checkOnTransactionLimit(Transaction inst) {
        TransactionLimit limit = transactionLimitService.getByExpenseCategoryAndMaxStandBy(inst.getExpenseCategory());
        BigDecimal transactionAmountWithCurrency;
        if(!inst.getCurrency().getShortName().equals("USD")) {
            BigDecimal actualCurrencyExchangeRate = exchangeService.getActualClosePriceForExchangeRate(
                    String.format("USD/%s", inst.getCurrency().getShortName()),
                    LocalDate.now()
            );
            transactionAmountWithCurrency = inst.getSum().multiply(actualCurrencyExchangeRate);
        }else{
            transactionAmountWithCurrency = inst.getSum();
        }

        if(transactionAmountWithCurrency.compareTo(limit.getAvailableAmount()) > 0){
            inst.setLimitExceeded(true);
            inst.setTransactionLimit(limit);
        }
        BigDecimal newAvailableAmount = limit.getAvailableAmount().subtract(inst.getSum());
        limit.setAvailableAmount(
                newAvailableAmount.max(BigDecimal.ZERO).equals(BigDecimal.ZERO) ? BigDecimal.ZERO : newAvailableAmount
        );
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
        BankAccount bankAccountFrom = bankAccountRepository.getByAddress(pojo.getAccountFrom());
        BankAccount bankAccountTo = bankAccountRepository.getByAddress(pojo.getAccountTo());

        BankAccountOwner accountFromOwner =
                bankAccountOwnerRepository.getOwnerByBankAccountAddress(pojo.getAccountFrom());

        BankAccountOwner accountToOwner =
                bankAccountOwnerRepository.getOwnerByBankAccountAddress(pojo.getAccountTo());

        if(bankAccountFrom == null){
            bankAccountFrom = new BankAccount();
            bankAccountFrom.setAddress(pojo.getAccountFrom());
            if(accountFromOwner == null){
                accountFromOwner = new BankAccountOwner();
                accountFromOwner.setOwnerType(BankAccountOwnerType.CLIENT);
                bankAccountOwnerRepository.save(accountFromOwner);
            }
            bankAccountFrom.setBankAccountOwner(accountFromOwner);
            bankAccountFrom = bankAccountRepository.save(bankAccountFrom);
        }

        if(bankAccountTo == null){
            bankAccountTo = new BankAccount();
            bankAccountTo.setAddress(pojo.getAccountTo());
            if(accountToOwner == null){
                accountToOwner = new BankAccountOwner();
                accountToOwner.setOwnerType(BankAccountOwnerType.CONTR_AGENT);
                bankAccountOwnerRepository.save(accountToOwner);
            }
            bankAccountTo.setBankAccountOwner(accountToOwner);
            bankAccountTo = bankAccountRepository.save(bankAccountTo);
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
