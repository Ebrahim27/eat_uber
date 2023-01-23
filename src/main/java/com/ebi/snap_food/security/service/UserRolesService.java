package com.ebi.snap_food.security.service;


import com.ebi.snap_food.infra.service.BaseService;
import com.ebi.snap_food.security.dto.UsersRolesDto;
import com.ebi.snap_food.security.model.Roles;
import com.ebi.snap_food.security.model.Users;
import com.ebi.snap_food.security.model.UsersRoles;
import com.ebi.snap_food.security.repository.UsersRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRolesService extends BaseService<UsersRoles, UsersRolesDto,Long> {
    @Autowired
    UsersRolesRepository repository;
    @Override
    public UsersRolesRepository getRepository() {
        return repository;
    }

    @Override
    public Class<UsersRolesDto> getDtoClazz() {
        return UsersRolesDto.class;
    }

    @Override
    public Class<UsersRoles> getEntityClazz() {
        return UsersRoles.class;
    }

    @Transactional
    public List<UsersRoles> findRoleByUserId(Long userId){
        return repository.getRoleByUser_id(userId);}

    @Transactional
    public List<UsersRoles> findUsersRolesByRole_id(Long roleId){
        return repository.getUsersRolesByRole_id(roleId);}

    @Transactional
    public List<UsersRoles> findUsersRoles(String roleName,Long  userId){
        return repository.getUsersRolesByUser_idAndRole_name(roleName,userId);}

    @Transactional
    public List<UsersRoles> findUsersRolesByName(String roleName,String username){
        return repository.getUsersRolesByUserNameAndRole_name(roleName,username);}

    @Transactional
    public List<UsersRoles> getUsersRolesByUser_idAndRole_id(Users user_id, Roles role_id){
        return repository.getUsersRolesByUser_idAndRole_id(user_id,role_id);}

}
