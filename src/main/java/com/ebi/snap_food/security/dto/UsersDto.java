package com.ebi.snap_food.security.dto;



import com.ebi.snap_food.security.model.Users;
import com.ebi.snap_food.infra.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class UsersDto extends BaseDto<Long, UsersDto, Users> {
    private String userName;
    private String fullname;
    private String mobileNo;
    private String password;
    private String activitycode;
   private boolean guest;
    private LocalDate birthDate;
    private String nationalCode;
    private String deviceType;



    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UsersDto setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }


    public String getNationalCode() {
        return nationalCode;
    }

    public UsersDto setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
        return this;
    }


    public boolean getGuest() {
        return guest;
    }

    public UsersDto setGuest(boolean guest) {
        this.guest = guest;
        return this;
    }

  @JsonIgnore
    public String getActivitycode() {
        return activitycode;
    }

    public UsersDto setActivitycode(String activitycode) {
        this.activitycode = activitycode;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public UsersDto() {
    }

    public UsersDto(String userName, String fullname, String mobile_no, String password, String activitycode) {
        this.userName = userName;
        this.fullname = fullname;
        this.mobileNo = mobile_no;
        this.password = password;
        this.activitycode= activitycode;
    }
   // @JsonIgnore
    public String getPassword() {
        return password;
    }

    public UsersDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UsersDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public UsersDto setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public UsersDto setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    @Override
    public Users convert(Class<Users> clazz) {
        return super.convert(clazz)
                .setFullname(getFullname())
                .setMobileNo(getMobileNo())
                .setUserName(getUserName())
                .setActivitycode(getActivitycode())
                .setPassword(getPassword())
                .setGuest(getGuest())
                .setBirthDate(getBirthDate())
                .setNationalCode(getNationalCode());
    }
}
