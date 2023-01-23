package com.ebi.snap_food.baseModels.dto;

import com.ebi.snap_food.baseModels.model.CarPrice;
import com.ebi.snap_food.baseModels.model.SystemType;
import com.ebi.snap_food.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class CarPriceDto extends BaseDto<Long, CarPriceDto, CarPrice> {
    private String model;
    private String type;
    private String type_en;
    private String description;
    private String year;
    private Long price;
    private String change_percent;
    private Boolean  market_price;
    private String last_update;
    private Integer keyField;
    private String hash;
    private SystemTypeDto map;


    @Override
    public CarPrice convert(Class<CarPrice> clazz) {
        return super.convert(clazz)
                .setMap(getMap() != null ? getMap().convert(SystemType.class) : null)
                .setModel(getModel())
                .setType(getType())
                .setType_en(getType_en())
                .setDescription(getDescription())
                .setYear(getYear())
                .setPrice(getPrice())
                .setChange_percent(getChange_percent())
                .setMarket_price(getMarket_price())
                .setLast_update(getLast_update())
                .setKeyField(getKeyField())
                .setHash(getHash());

    }
}