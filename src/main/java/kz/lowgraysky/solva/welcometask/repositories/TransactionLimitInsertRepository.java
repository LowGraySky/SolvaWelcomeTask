package kz.lowgraysky.solva.welcometask.repositories;

import jakarta.transaction.Transactional;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionLimitInsertRepository extends CommonInsertRepository<TransactionLimit>{

    @Transactional
    @Override
    public void insert(TransactionLimit inst) {
        this.entityManager.persist(inst);
    }
}
