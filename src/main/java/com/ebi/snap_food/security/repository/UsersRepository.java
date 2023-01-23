package com.ebi.snap_food.security.repository;


import com.ebi.snap_food.infra.repository.BaseCrudRepository;
import com.ebi.snap_food.security.dto.UsersDto;
import com.ebi.snap_food.security.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends BaseCrudRepository<Users, Long, UsersDto> {
    @Query("SELECT u FROM Users u WHERE u.mobileNo = :mobile_no")
    public Users getUserByPhoneNumber(@Param("mobile_no") String mobile_no);

    @Query("SELECT u FROM Users u WHERE u.userName = :username")
    public Users getUserByUsername(@Param("username") String username);


    @Query("SELECT   u FROM Users u WHERE u.userName = :username")
    public Users getUseryUsername(@Param("username") String username);

    List<Users> findByFullnameContaining(String name);
    List<Users> findByNationalCodeContaining(String name);

    @Query("SELECT u.userId FROM UsersRoles u inner join Roles r on r.id=u.roleId.id where r.roleAbbreviation= :roleAbbreviation ")
    public List<Users> getUsersByRoleAbbreviation(@Param("roleAbbreviation") String roleAbbreviation  ) ;

    public Page<Users> findAll(Specification<Users> spec, Pageable page);
    public List<Users> findAll(Specification<Users> spec);


}