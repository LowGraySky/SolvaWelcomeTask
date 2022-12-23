package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends EntityRepository<Transaction> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.limitExceeded = TRUE")
    List<Transaction> getAllTransactionsWithTimeLimitExceed();

    @Query(value = "SELECT t FROM Transaction t where t.accountFrom = :address OR t.accountTo = :address")
    List<Transaction> getByAccountAddress(Integer address);

    @Query(value = "SELECT t FROM Transaction t where t.accountFrom = :from AND t.accountTo = :to")
    List<Transaction> getByAccountAddress(Integer from, Integer to);
}
