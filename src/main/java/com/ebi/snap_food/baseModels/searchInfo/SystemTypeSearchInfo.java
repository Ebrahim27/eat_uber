package com.ebi.snap_food.baseModels.searchInfo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;



@Getter
@Setter
@Accessors(chain = true)
public class SystemTypeSearchInfo {

     private String name;
     private String map;
    private Integer count;
    private Integer pageNumber;

}
