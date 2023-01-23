package com.ebi.snap_food.security.repository;



import com.ebi.snap_food.security.model.Roles;
import com.ebi.snap_food.infra.repository.BaseCrudRepository;
import com.ebi.snap_food.security.dto.RolesDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RolesRepository extends BaseCrudRepository<Roles,Long, RolesDto> {
    @Query("SELECT u FROM Roles u where u.roleAbbreviation= :roleName ")
    public Roles getRolesByRole_name(@Param("roleName") String roleName );

    @Query("SELECT u FROM Roles u where u.roleName= :roleName ")
    public List<Roles> getRolesListByRole_name(@Param("roleName") String roleName );


}
