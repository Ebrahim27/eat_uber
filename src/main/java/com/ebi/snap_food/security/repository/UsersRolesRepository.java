package com.ebi.snap_food.security.repository;



import com.ebi.snap_food.security.model.UsersRoles;
import com.ebi.snap_food.infra.repository.BaseCrudRepository;
import com.ebi.snap_food.security.dto.UsersRolesDto;
import com.ebi.snap_food.security.model.Roles;
import com.ebi.snap_food.security.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRolesRepository extends BaseCrudRepository<UsersRoles,Long, UsersRolesDto> {
    @Query("SELECT u FROM UsersRoles u WHERE u.userId.id = :userId")
    public List<UsersRoles> getRoleByUser_id(@Param("userId") Long userId);

    @Query("SELECT u FROM UsersRoles u WHERE u.roleId .id= :roleId")
    public List<UsersRoles> getUsersRolesByRole_id(@Param("roleId") Long roleId);

    @Query("SELECT u FROM UsersRoles u inner join Roles r on r.id=u.roleId.id where r.roleName= :roleName and u.userId.id= :userId")
    public List<UsersRoles> getUsersRolesByUser_idAndRole_name(@Param("roleName") String roleName , @Param("userId") Long userId);

    @Query("SELECT u FROM UsersRoles u inner join Roles r on r.id=u.roleId.id where r.roleName= :roleName and u.userId.userName= :username")
    public List<UsersRoles> getUsersRolesByUserNameAndRole_name(@Param("roleName") String roleName , @Param("username") String username);

    @Query("SELECT u FROM UsersRoles u  where u.userId= :user_id and u.roleId=:role_id")
    public List<UsersRoles>   getUsersRolesByUser_idAndRole_id(@Param("user_id") Users user_id , @Param("role_id") Roles role_id );

}
