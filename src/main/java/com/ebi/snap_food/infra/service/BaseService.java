package com.ebi.snap_food.infra.service;


import com.ebi.snap_food.infra.dto.BaseDto;
import com.ebi.snap_food.infra.model.BaseEntity;
import com.ebi.snap_food.infra.repository.BaseCrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseService<E extends BaseEntity<ID, D, E>, D extends BaseDto<ID, D, E>, ID extends Number> {

    public abstract BaseCrudRepository<E, ID, D> getRepository();

    public abstract Class<D> getDtoClazz();

    public abstract Class<E> getEntityClazz();

    @Transactional
    public D findById(ID id) {

        return getRepository().findById(id).map(e -> e.convert(getDtoClazz())).orElse(null);
    }

    @Transactional
    public List<D> findAll() {

        List<D> providers = new ArrayList<>();
        getRepository().findAll().forEach(e -> providers.add(e.convert(getDtoClazz())));
        return providers;
    }

    @Transactional
    public D save(D dto) {

        return getRepository().save(dto.convert(getEntityClazz())).convert(getDtoClazz());
    }

    @Transactional
    public void update(D dto) {

        getRepository().save(dto.convert(getEntityClazz()));
    }

    public void delete(ID id) {

        getRepository().deleteById(id);
    }


}
