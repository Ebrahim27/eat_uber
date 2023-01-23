package com.ebi.snap_food.security.dto;


import com.ebi.snap_food.security.model.Roles;
import com.ebi.snap_food.security.model.Users;
import com.ebi.snap_food.security.model.UsersRoles;
import com.ebi.snap_food.infra.dto.BaseDto;

public class UsersRolesDto extends BaseDto<Long, UsersRolesDto, UsersRoles> {
    private UsersDto userId;
    private RolesDto roleId;



    public UsersRolesDto() {
    }

    public UsersRolesDto(UsersDto user_id, RolesDto role_id) {
        this.userId = user_id;
        this.roleId = role_id;

    }

    public UsersDto getUserId() {
        return userId;
    }

    public UsersRolesDto setUserId(UsersDto userId) {
        this.userId = userId;
        return this;
    }

    public RolesDto getRoleId() {
        return roleId;
    }

    public UsersRolesDto setRoleId(RolesDto roleId) {
        this.roleId = roleId;
        return this;
    }


    @Override
    public UsersRoles convert(Class<UsersRoles> clazz) {
        return super.convert(clazz)

                .setRoleId(getRoleId() !=null ? getRoleId().convert(Roles.class) : null)
                .setUserId(getUserId() !=null ? getUserId().convert(Users.class) : null);
    }
}
