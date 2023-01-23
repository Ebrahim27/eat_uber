package com.ebi.snap_food.infra.model;



import com.ebi.snap_food.infra.dto.BaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;


@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable, D extends BaseDto<ID, D, E>, E extends BaseEntity<ID, D, E>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntity.class);

    private ID id;

    @Id
    @GeneratedValue
    @Column(name = "Id")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public D convert(Class<D> clazz) {

        LOGGER.debug("converting");
        D dto = null;
        try {
            dto = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }
        Objects.requireNonNull(dto).setId(this.getId());
        return dto;
    }
}
