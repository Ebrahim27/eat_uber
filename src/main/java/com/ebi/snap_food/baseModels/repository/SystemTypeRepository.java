package com.ebi.snap_food.baseModels.repository;

import com.ebi.snap_food.baseModels.dto.SystemTypeDto;
import com.ebi.snap_food.baseModels.model.SystemType;
import com.ebi.snap_food.infra.repository.BaseCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SystemTypeRepository  extends BaseCrudRepository<SystemType, Long, SystemTypeDto> {
}
