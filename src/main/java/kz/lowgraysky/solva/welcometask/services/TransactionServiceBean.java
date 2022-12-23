package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import kz.lowgraysky.solva.welcometask.entities.BankAccountOwner;
import kz.lowgraysky.solva.welcometask.entities.Currency;
import kz.lowgraysky.solva.welcometask.entities.Transaction;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.pojos.TransactionPojo;
import kz.lowgraysky.solva.welcometask.repositories.*;
import kz.lowgraysky.solva.welcometask.utils.BeanHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceBean extends BeanHelper implements TransactionsService{

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    private final CurrencyServiceBean currencyService;
    private final CurrencyRepository currencyRepository;

    public TransactionServiceBean(TransactionRepository transactionRepository,
                                  BankAccountRepository bankAccountRepository,
                                  BankAccountOwnerRepository bankAccountOwnerRepository,
                                  CurrencyServiceBean currencyService,
                                  CurrencyRepository currencyRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountOwnerRepository = bankAccountOwnerRepository;
        this.currencyService = currencyService;
        this.currencyRepository = currencyRepository;
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
    public List<Transaction> getByBankAccount(Integer address) {
        return transactionRepository.getByAccountAddress(address);
    }

    @Override
    public List<Transaction> getByBankAccount(Integer from, Integer to) {
        return transactionRepository.getByAccountAddress(from, to);
    }

    @Override
    public List<Transaction> getAllTransactionWithTimeLimitExceed() {
        return transactionRepository.getAllTransactionsWithTimeLimitExceed();
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
        transaction.setSum(pojo.getSum());
        return transaction;
    }
}
