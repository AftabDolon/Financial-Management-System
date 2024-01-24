package com.demo.task.repository;

import com.demo.task.auth.dto.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {
    Optional<SecurityUser> findByUsername(String username);

    @Query("SELECT u FROM SecurityUser u WHERE u.id IN(:ids)")
    List<SecurityUser> getDataByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT ROLE_ID\n" +
            "  FROM SEC_USER_ROLES\n" +
            " WHERE USER_ID = :userId", nativeQuery = true)
    List<Long> findRoleId(@Param("userId") Long userId);

    @Query(value = "SELECT U.USER_ID, U.USER_NAME,CASE  WHEN U.USER_TYPE_ID = 1 THEN 'INTERNAL'\n" +
            "        ELSE 'EXTERNAL'\n" +
            "    END AS USER_TYPE,\n" +
            "                           (SELECT concat(FIRST_NAME, LAST_NAME) name\n" +
            "                              FROM task.SEC_EMPLOYEES\n" +
            "                             WHERE task.SEC_EMPLOYEES.EMP_ID = U.EMP_ID)\n" +
            "                               EMP_NAME,\n" +
            "                           (SELECT DEPARTMENT\n" +
            "                              FROM task.SEC_EMPLOYEES\n" +
            "                             WHERE task.SEC_EMPLOYEES.EMP_ID = U.EMP_ID)\n" +
            "                               DEPARTMENT_NAME,\n" +
            "                           (SELECT DESIGNATION\n" +
            "                              FROM task.SEC_EMPLOYEES\n" +
            "                             WHERE task.SEC_EMPLOYEES.EMP_ID = U.EMP_ID)\n" +
            "                               DESIGNATION,\n" +
            "                           U.EMAIL,\n" +
            "                           U.IS_ACTIVE\n" +
            "FROM task.USERS U\n" +
            "                     WHERE     U.USER_TYPE_ID = COALESCE (:userType, U.USER_TYPE_ID)", nativeQuery = true)
    List<Object[]> getUserList(@Param("userType") String userType);

    @Query(value = "SELECT \n" +
            "    USER_ID,\n" +
            "    CASE\n" +
            "        WHEN\n" +
            "            USER_TYPE_ID = 1\n" +
            "        THEN\n" +
            "            (SELECT \n" +
            "                    CONCAT(FIRST_NAME, LAST_NAME)\n" +
            "                FROM\n" +
            "                    task.SEC_EMPLOYEES\n" +
            "                WHERE\n" +
            "                    EMP_ID = U.EMP_ID)\n" +
            "        WHEN USER_TYPE_ID = 2 THEN USER_NAME\n" +
            "    END AS employeeName\n" +
            "FROM\n" +
            "    task.USERS U\n" +
            "WHERE\n" +
            "    USER_ID = :userId", nativeQuery = true)
    Map<String, Object> getUserDisplayProfile(@Param("userId") Long userId);
}
