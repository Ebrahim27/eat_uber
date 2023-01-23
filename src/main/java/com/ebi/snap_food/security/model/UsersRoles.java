package com.ebi.snap_food.security.model;





import com.ebi.snap_food.infra.model.BaseEntity;
import com.ebi.snap_food.security.dto.RolesDto;
import com.ebi.snap_food.security.dto.UsersDto;
import com.ebi.snap_food.security.dto.UsersRolesDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users_roles")
public class UsersRoles extends BaseEntity<Long, UsersRolesDto, UsersRoles> {
    private Users userId;
    private Roles roleId;


    public UsersRoles() {
    }

    public UsersRoles(Users user_id, Roles role_id, int active, boolean isdeleted, LocalDate inser_date, LocalDate last_update_date) {
        this.userId = user_id;
        this.roleId = role_id;

    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Users.class)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_userroles_users"))
    public Users getUserId() {
        return userId;
    }

    public UsersRoles setUserId(Users user_id) {
        this.userId = user_id;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_usersroles_roles"))
    public Roles getRoleId() {
        return roleId;
    }

    public UsersRoles setRoleId(Roles role_id) {
        this.roleId = role_id;
        return this;
    }





    @Override
    public UsersRolesDto convert(Class<UsersRolesDto> clazz) {
        return super.convert(clazz)

                .setRoleId(getRoleId() !=null ? getRoleId().convert(RolesDto.class) : null)
                .setUserId(getUserId() !=null ? getUserId().convert(UsersDto.class) : null);
    }
}
