package com.ebi.snap_food.infra.dto;


import com.ebi.snap_food.infra.model.BaseEntity;

public interface BaseIndependentDto<ID extends Number, D extends BaseDto<ID, D, E>, E extends BaseEntity<ID, D, E>> {


}
