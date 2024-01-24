package com.demo.task.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SEC_SUBMENU")
public class SubMenu implements Serializable {
    public static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBMENU_ID", unique = true, nullable = false)
    private Long submenuId;
    @NotBlank(message = "SUBMENU NAME CAN NOT BE BLANK!")
    @Column(name = "SUBMENU_NAME")
    private String submenuName;
    @Column(name = "SUBMENU_TEXT_ENG")
    private String submenuTextEng;
    //    @Column(name = "PARENT_SUBMENU_ID")
//    private Long parentSubMenuId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "MENU_ID", nullable = false)
    private Menu menu;
    @Column(name = "CONTROLLER_NAME")
    private String controllerName;
    @Column(name = "ACTION_NAME")
    private String actionName;
    @NotBlank(message = "ROUTE NAME CAN NOT BE BLANK!")
    @Column(name = "ROUTE_NAME")
    private String routeName;
    @Column(name = "MENU_ORDER_NO")
    private Integer menuOrderNo;
    @Column(name = "ACTIVE_YN", nullable = false)
    private String activeYn;

    @Transient
    private String menuName;

    @Transient
    private Long menuId;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//    @JoinTable(name = "sec_role_menus", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "MENU_ID")})
//    private Set<SecurityRole> roles;

    public boolean hasId() {
        return submenuId != null && submenuId > 0;
    }

    public SubMenu() {
    }

    public SubMenu(Tuple tuple) {
        submenuId = tuple.get("SUBMENU_ID", Long.class);
        submenuName = tuple.get("SUBMENU_NAME", String.class);
        submenuTextEng = tuple.get("SUBMENU_TEXT_ENG", String.class);
        menuId = tuple.get("MENU_ID", Long.class);
        menuName = tuple.get("MENU_NAME", String.class);
        menuOrderNo = tuple.get("MENU_ORDER_NO", Integer.class);
        activeYn = tuple.get("ACTIVE_YN", String.class);
        controllerName = tuple.get("CONTROLLER_NAME", String.class);
        routeName = tuple.get("ROUTE_NAME", String.class);
        actionName = tuple.get("ACTION_NAME", String.class);

    }

}
