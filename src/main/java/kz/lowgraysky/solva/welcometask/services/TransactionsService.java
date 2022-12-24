package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.Transaction;

import java.util.List;

public interface TransactionsService extends CommonService<Transaction>{

    void insert(Transaction inst);

    Transaction setLimitInformation(Transaction inst);

    List<Transaction> getByBankAccount(Integer address);

    List<Transaction> getByBankAccount(Integer from, Integer to);

    List<Transaction> getAllTransactionWithTimeLimitExceed();
}
