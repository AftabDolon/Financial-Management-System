package com.demo.task.repository;

import com.demo.task.model.Menu;
import com.demo.task.model.RoleMenu;
import com.demo.task.model.RoleMenuId;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuId> {

    //    Optional<RoleMenu> findByRoleIdAndMenuId(Long roleId, Long menuId);
    Optional<RoleMenu> findById(RoleMenuId id);

    @Query(value = "SELECT MENU_ID, ACTIVE_YN, SUBMENUS FROM SEC_ROLE_MENUS WHERE ROLE_ID = :roleId", nativeQuery = true)
    List<Tuple> getRoleMenuList(@Param("roleId") Integer roleId);

    @Query("SELECT rm FROM RoleMenu rm WHERE rm.id.roleId = :roleId")
    List<RoleMenu> findAllByRoleId(Long roleId);

//    @Query("SELECT m FROM Menu m " +
//            "JOIN RoleMenu rm ON m.menuId = rm.id.menuId " +
//            "LEFT JOIN SubMenu sm ON m.menuId = sm.menu.menuId " +
//            "WHERE rm.id.roleId = :roleId")
//    List<Menu> findMenusAndSubmenusByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT DISTINCT m " +
            "FROM Menu m " +
            "JOIN RoleMenu rm ON m.menuId = rm.id.menuId " +
            "LEFT JOIN SubMenu sm ON m.menuId = sm.menu.menuId " +
            "WHERE rm.id.roleId IN :roleIds")
    List<Menu> findMenusWithSpecificSubmenus(@Param("roleIds") List<Long> roleIds);

    @Query("SELECT DISTINCT m FROM Menu m " +
            "JOIN RoleMenu rm ON m.menuId = rm.id.menuId " +
            "LEFT JOIN SubMenu sm ON m.menuId = sm.menu.menuId " +
            "WHERE rm.id.roleId IN :roleIds")
    List<Menu> findMenusAndSubmenusByRoleIds(@Param("roleIds") List<Long> roleIds);

    @Query(value = "SELECT COUNT(*) FROM SEC_ROLE_MENUS WHERE ROLE_ID = :roleId AND FIND_IN_SET(:submenuId, SUBMENUS) > 0", nativeQuery = true)
    Long countBySubMenu_SubmenuIdAndRole_RoleId(@Param("submenuId") Long submenuId, @Param("roleId") Long roleId);

    List<RoleMenu> findByIdRoleId(Long roleId);

    @Query(value = "SELECT SUBMENUS FROM SEC_ROLE_MENUS WHERE ROLE_ID IN :roleId AND MENU_ID = :menuId", nativeQuery = true)
    String findSubmenusByRoleIdAndMenuId(@Param("roleId") List<Long> roleId, @Param("menuId") Long menuId);

}
