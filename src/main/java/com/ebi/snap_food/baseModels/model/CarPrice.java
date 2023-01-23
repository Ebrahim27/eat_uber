package com.ebi.snap_food.baseModels.model;


import com.ebi.snap_food.baseModels.dto.CarPriceDto;
import com.ebi.snap_food.baseModels.dto.SystemTypeDto;
import com.ebi.snap_food.infra.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "car_price")
public class CarPrice extends BaseEntity<Long, CarPriceDto, CarPrice> {

    private String model;
    private String type;
    private String type_en;
    private String description;
    private String year;
    private Long price;
    private String change_percent;
    private Boolean  market_price;
    private String last_update;
    private Integer keyField;//key has changed to keyField because of Mysql
    private String hash;
    private SystemType map;
//    {
//        "model": "207‏، اتوماتیک",
//            "type": "پژو",
//            "type_en": "peugeot",
//            "description": "با رینگ فولادی - ایران خودرو",
//            "year": "1400",
//            "price": "660000000",
//            "change_percent": "1.52",
//            "market_price": true,
//            "last_update": "5 ساعت پیش",
//            "key": 1097,
//            "hash": "453b226708cc639066f4071e0783e247"
//    }

    @Column(name = "model")
    public String getModel(){
     return  model;
 }
    @Column(name = "type")
    public String getType(){
        return  type;
    }

    @Column(name = "type_en")
    public String getType_en(){
        return  type_en;
    }

    @Column(name = "description")
    public String getDescription(){
        return  description;
    }

    @Column(name = "year")
    public String getYear(){
        return  year;
    }

    @Column(name = "price")
    public Long getPrice(){
        return  price;
    }

    @Column(name = "change_percent")
    public String getChange_percent(){
        return  change_percent;
    }

    @Column(name = "market_price")
    public Boolean getMarket_price(){
        return  market_price;
    }

    @Column(name = "last_update")
    public String getLast_update(){
        return  last_update;
    }

    @Column(name = "keyField")
    public Integer getKeyField(){
        return  keyField;
    }

    @Column(name = "hash")
    public String getHash(){
        return  hash;
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = SystemType.class)
    @JoinColumn(name = "map", foreignKey = @ForeignKey(name = "fk_system_type"))
    public SystemType getMap() {
        return map;
    }



    @Override
    public CarPriceDto convert(Class<CarPriceDto> clazz){
        return super.convert(clazz)
                .setMap(getMap() != null ? getMap().convert(SystemTypeDto.class) : null)
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
