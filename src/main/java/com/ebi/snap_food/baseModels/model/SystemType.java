package com.ebi.snap_food.baseModels.model;


import com.ebi.snap_food.baseModels.dto.SystemTypeDto;
import com.ebi.snap_food.infra.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "system_type")
public class SystemType extends BaseEntity<Long, SystemTypeDto, SystemType> {


    private String name;
    private String map;



    @Column(name = "name")
    public String getName(){
        return  name;
    }

    @Column(name = "map")
    public String getMap(){
        return  map;
    }



    @Override
    public SystemTypeDto convert(Class<SystemTypeDto> clazz) {
        return super.convert(clazz)
                .setName(getName())
                .setMap(getMap());
    }
}
