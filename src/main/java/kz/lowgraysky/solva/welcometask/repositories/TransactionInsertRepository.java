package kz.lowgraysky.solva.welcometask.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import kz.lowgraysky.solva.welcometask.entities.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insert(Transaction transaction){
        this.entityManager.persist(transaction);
    }
}
