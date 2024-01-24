package com.demo.task.repository;

import com.demo.task.auth.dto.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<SecurityRole, Long> {


    @Query(value = "SELECT ROLE_ID, UPPER(ROLE_NAME), ROLE_KEY, ACTIVE_YN, GRANT_ALL_YN FROM SEC_ROLE", nativeQuery = true)
    List<Object[]> getRoles();

    @Query(value = "select count(*) from SEC_ROLE where ROLE_NAME = 'Admin'", nativeQuery = true)
    int countByAdminRole();

    @Query(value = "select count(*) from SEC_ROLE where ROLE_NAME = 'User'", nativeQuery = true)
    int countByUserRole();
}
