package kz.lowgraysky.solva.welcometask.repositories;

import jakarta.transaction.Transactional;
import kz.lowgraysky.solva.welcometask.entities.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionInsertRepository extends CommonInsertRepository<Transaction>{

    @Transactional
    @Override
    public void insert(Transaction inst) {
        this.entityManager.persist(inst);
    }
}
