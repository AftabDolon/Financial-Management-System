package com.demo.task.repository;

import com.demo.task.model.SubMenu;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {

    @Query("SELECT " +
            "(SELECT COUNT(sm) FROM SubMenu sm WHERE UPPER(sm.submenuName) = :submenuName) as nameCount, " +
            "(SELECT COUNT(sm) FROM SubMenu sm WHERE sm.menuOrderNo = :menuOrder AND sm.menu.id = :menuId) as orderCount")
    List<Object[]> findSubMenuNameAndMenuOrderNumberExists(@Param("submenuName") String submenuName, @Param("menuOrder") Integer menuOrder, @Param("menuId") Long menuId);

    @Query(value = "SELECT SUBMENU_ID,\n" +
            "       SUBMENU_NAME,\n" +
            "       SUBMENU_TEXT_ENG,\n" +
            "       SUBMENU_TEXT_BNG,\n" +
            "       MENU_ID,\n" +
            "       (SELECT MENU_NAME\n" +
            "          FROM SEC_MENU\n" +
            "         WHERE MENU_ID = SEC_SUBMENU.MENU_ID)\n" +
            "           MENU_NAME,\n" +
            "       CONTROLLER_NAME,\n" +
            "       ACTION_NAME,\n" +
            "       ROUTE_NAME,\n" +
            "       ACTIVE_YN,\n" +
            "       MENU_ORDER_NO\n" +
            "  FROM task.SEC_SUBMENU", nativeQuery = true)
    List<Tuple> getSubMenuInformation();

    @Query("SELECT sm FROM SubMenu sm " +
            "WHERE sm.submenuId IN :submenuIds ORDER BY sm.menuOrderNo")
    List<SubMenu> findSubmenusByIds(@Param("submenuIds") List<Long> submenuIds);


//    @Query("SELECT DISTINCT sm FROM SubMenu sm " +
//            "JOIN RoleMenu rm ON rm.id.menuId = sm.menu.menuId " +
//            "WHERE sm.menu.menuId = :menuId AND rm.id.roleId IN :roleIds " +
//            "ORDER BY sm.menuOrderNo")
//    List<SubMenu> findSubMenusByMenuIdAndRoleIds(@Param("menuId") Long menuId, @Param("roleIds") List<Long> roleIds);
}
