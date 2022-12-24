package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.Transaction;

import java.util.List;

public interface TransactionsService extends CommonService<Transaction>{

    void checkOnTransactionLimit(Transaction inst);

    void insert(Transaction inst);

    List<Transaction> getAllTransactionWithTimeLimitExceed();
}
