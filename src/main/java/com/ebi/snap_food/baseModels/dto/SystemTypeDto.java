package com.ebi.snap_food.baseModels.dto;


import com.ebi.snap_food.baseModels.model.SystemType;
import com.ebi.snap_food.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class SystemTypeDto extends BaseDto<Long, SystemTypeDto, SystemType> {

    private String name;
    private String map;




    @Override
    public SystemType convert(Class<SystemType> clazz) {
        return super.convert(clazz)
                .setName(getName())
                .setMap(getMap());
    }

}
