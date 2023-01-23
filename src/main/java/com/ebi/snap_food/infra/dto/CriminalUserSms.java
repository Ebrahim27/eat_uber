package com.ebi.snap_food.infra.dto;


import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CriminalUserSms {

    private String userName;
    private String fullname;
    private String mobileNo;
    private String pelak;
    private  String driverName;
    private String nationalCode;
    private String policyNo;




}
