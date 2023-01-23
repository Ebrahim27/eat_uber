package com.ebi.snap_food.infra.dto;


import com.ebi.snap_food.infra.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;


public abstract class BaseDto<ID extends Serializable, D extends BaseDto<ID, D, E>, E extends BaseEntity<ID, D, E>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntity.class);

    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public E convert(Class<E> clazz) {

        E entity = null;
        try {
            entity = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }

        Objects.requireNonNull(entity).setId(this.getId());
        return entity;
    }
}
