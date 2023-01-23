package com.ebi.snap_food.baseModels.searchInfo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CarPriceSearchInfo {

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
    private Integer count;
    private Integer pageNumber;
}
