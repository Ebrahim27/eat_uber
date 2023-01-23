package com.ebi.snap_food.infra.repository;



import com.ebi.snap_food.infra.dto.BaseDto;
import com.ebi.snap_food.infra.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.Optional;


public interface BaseCrudRepository<E extends BaseEntity<L, D, E>, L extends Serializable, D extends BaseDto<L, D, E>> extends JpaRepository<E, L>, JpaSpecificationExecutor<E> {


    <S extends E> S save(S entity);

    Optional<E> findById(L id);


    void deleteById(L id);
}
