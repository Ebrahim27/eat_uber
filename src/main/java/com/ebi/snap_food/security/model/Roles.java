package com.ebi.snap_food.security.model;



import com.ebi.snap_food.infra.model.BaseEntity;
import com.ebi.snap_food.security.dto.RolesDto;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles extends BaseEntity<Long, RolesDto, Roles> {
    private String roleName;
    private String roleAbbreviation;


    public Roles() {
    }



    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public Roles setRoleName(String role_name) {
        this.roleName = role_name;
        return this;
    }

    @Column(name = "role_abbreviation")
    public String getRoleAbbreviation() {
        return roleAbbreviation;
    }

    public Roles setRoleAbbreviation(String roleAbbreviation) {
        this.roleAbbreviation = roleAbbreviation;
        return this;
    }

    @Override
    public RolesDto convert(Class<RolesDto> clazz) {
        return super.convert(clazz)
                .setRoleName(getRoleName())
                .setRoleAbbreviation(getRoleAbbreviation());
    }
}
