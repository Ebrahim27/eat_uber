package com.ebi.snap_food.security.service;

import com.ebi.snap_food.security.dto.RolesDto;
import com.ebi.snap_food.security.model.Roles;
import com.ebi.snap_food.security.repository.RolesRepository;
import com.ebi.snap_food.infra.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesService extends BaseService<Roles, RolesDto,Long> {

    @Autowired
    RolesRepository repository;

    @Override
    public RolesRepository getRepository() {
        return repository;
    }

    @Override
    public Class<RolesDto> getDtoClazz() {
        return RolesDto.class;
    }

    @Override
    public Class<Roles> getEntityClazz() {
        return Roles.class;
    }

    @Transactional
    public Roles getRolesByRole_name(String roleName) {
        return repository.getRolesByRole_name(roleName);
    }



    @Transactional
    public List<Roles> getRolesListByRole_name(String roleName) {
        return repository.getRolesListByRole_name(roleName);
    }

}