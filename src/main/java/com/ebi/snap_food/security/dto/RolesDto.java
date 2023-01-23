package com.ebi.snap_food.security.dto;


import com.ebi.snap_food.security.model.Roles;
import com.ebi.snap_food.infra.dto.BaseDto;

public class RolesDto extends BaseDto<Long, RolesDto, Roles> {
    private String roleName;
    private String roleAbbreviation;


    public RolesDto() {
    }



    public String getRoleName() {
        return roleName;
    }

    public RolesDto setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getRoleAbbreviation() {
        return roleAbbreviation;
    }

    public RolesDto setRoleAbbreviation(String roleAbbreviation) {
        this.roleAbbreviation = roleAbbreviation;
        return this;
    }

    @Override
    public Roles convert(Class<Roles> clazz) {
        return super.convert(clazz)
                .setRoleName(getRoleName())
                .setRoleAbbreviation(getRoleAbbreviation());
    }
}
