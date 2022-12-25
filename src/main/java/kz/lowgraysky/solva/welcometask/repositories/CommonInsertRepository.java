package kz.lowgraysky.solva.welcometask.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kz.lowgraysky.solva.welcometask.entities.BaseEntity;

public abstract class CommonInsertRepository<T extends BaseEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    public abstract void insert(T inst);
}
