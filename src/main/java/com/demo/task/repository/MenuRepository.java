package com.demo.task.repository;

import com.demo.task.model.Menu;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    //    @Query(value = "SELECT m FROM Menu m JOIN FETCH m.subMenus sm WHERE sm.activeYn = 'Y'")
    @Query(value = "SELECT m FROM Menu m LEFT JOIN FETCH m.subMenus sm WHERE m.activeYn = 'Y' ORDER BY m.menuOrderNo, sm.menuOrderNo")
    List<Menu> findAllMenu();

//    @Query(value = "select submenus from SBCBI_APP_SECURITY.sec_role_menus\n" +
//            "where role_id = (select ROLE_ID from sec_user_roles where USER_ID = \n" +
//            "(select user_id from users where user_name = 'cnsadmin'))", nativeQuery = true)
//    Object[][] getSubMenus();


//    @Query("SELECT " +
//            "(SELECT COUNT(m) FROM Menu m WHERE UPPER(m.menuName) = :menuName) as nameCount, " +
//            "(SELECT COUNT(m) FROM Menu m WHERE m.menuOrderNo = :menuOrder) as orderCount")
//    List<Object[]> findMenuNameAndMenuOrderNumberExists(@Param("menuName") String menuName, @Param("menuOrder") Integer menuOrder);
//
//    @Query("SELECT m.menuOrderNo FROM Menu m WHERE m.menuOrderNo = :menuOrderNo")
//    Integer getMenuOrderNo(@Param("menuOrderNo") Integer menuOrderNo);

    @Query(value = "SELECT MENU_ID, MENU_NAME, MENU_TEXT_ENG, "
            + "MENU_ORDER_NO, ACTIVE_YN, BASE_URL, BASE_PATH, ICON, BG_COLOR, DASHBOARD_VISIBLE_YN "
            + "FROM task.SEC_MENU", nativeQuery = true)
    List<Tuple> findMenuInformation();

//    @Query(value = "SELECT NEW com.cnsbd.bisdp_bi_sbc.model.dto.MenuIdNameDTO(m.menuId, m.menuName) FROM Menu m")
//    List<MenuIdNameDTO> findByMenuIdAndMenuName();

//    @Query("SELECT m FROM Menu m WHERE EXISTS (SELECT rm FROM RoleMenu rm WHERE rm.id.menuId = m.menuId AND rm.id.roleId = :roleId)")
//    List<Menu> findMenusByRoleId(@Param("roleId") Long roleId);


//    @Query("SELECT DISTINCT m FROM Menu m " +
//            "JOIN RoleMenu rm ON m.menuId = rm.id.menuId " +
//            "WHERE rm.id.roleId IN :roleIds ORDER BY m.menuOrderNo")

    @Query("SELECT DISTINCT m FROM Menu m " +
            "JOIN RoleMenu rm ON m.menuId = rm.id.menuId " +
            "WHERE rm.id.roleId IN :roleIds AND m.activeYn = 'Y' ORDER BY m.menuOrderNo")
    List<Menu> findMenusByRoleIds(@Param("roleIds") List<Long> roleIds);
}
