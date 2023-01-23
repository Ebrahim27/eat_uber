package com.ebi.snap_food.security.model;




import com.ebi.snap_food.infra.model.BaseEntity;
import com.ebi.snap_food.security.dto.UsersDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class Users extends BaseEntity<Long, UsersDto, Users> {
    private String userName;
    private String fullname;
    private String mobileNo;
    private String password;
    private String activitycode;
    private boolean guest;
    private LocalDate birthDate;
    private String nationalCode;



    @Column(name = "birth_date")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Users setBirthDate(LocalDate birth_date) {
        this.birthDate = birth_date;
        return this;
    }

    @Column(name = "national_code",columnDefinition="varchar(12)" )
    public String getNationalCode() {
        return nationalCode;
    }

    public Users setNationalCode(String national_code) {
        this.nationalCode = national_code;
        return this;
    }

    @Column(name = "isguest")
    public boolean getGuest() {
        return guest;
    }

    public Users setGuest(boolean guest) {
        this.guest = guest;
        return this;
    }
    @JsonIgnore
    @Column(name = "activitycode")
    public String getActivitycode() {
        return activitycode;
    }

    public Users setActivitycode(String activitycode) {
        this.activitycode = activitycode;
        return this;
    }
    @JsonIgnore
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public Users() {
    }

  public Users(String userName, String fullname, String mobile_no, String activitycode) {
        this.userName = userName;
        this.fullname = fullname;
        this.mobileNo = mobile_no;
        this.activitycode=activitycode;
    }

    @Column(name = "username")
    public String getUserName() {
        return userName;
    }

    public Users setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Column(name = "fullname")
    public String getFullname() {
        return fullname;
    }

    public Users setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    @Column(name = "mobile_no")
    public String getMobileNo() {
        return mobileNo;
    }

    public Users setMobileNo(String mobile_no) {
        this.mobileNo = mobile_no;
        return this;
    }



    @Override
    public UsersDto convert(Class<UsersDto> clazz) {
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
