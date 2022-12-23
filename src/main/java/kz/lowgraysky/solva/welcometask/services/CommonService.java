package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.BaseEntity;

public interface CommonService<T extends BaseEntity>{

    T save(T inst);

    T getById(long id);
}
