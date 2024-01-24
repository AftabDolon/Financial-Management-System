package com.demo.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "SEC_MENU")
public class Menu implements Serializable {
    public static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID", unique = true, nullable = false)
    private Long menuId;
    @Column(name = "MENU_NAME")
    private String menuName;
    @Column(name = "ACTIVE_YN")
    private String activeYn;
    @Column(name = "BASE_PATH")
    private String basePath;
    @Column(name = "BASE_URL")
    private String baseUrl;
    @Column(name = "BG_COLOR")
    private String bgColor;
    @Column(name = "dashboard_visible_yn")
    private String dashboardVisibleYn;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "MENU_ORDER_NO")
    private Integer menuOrderNo;
    @Column(name = "MENU_TEXT_ENG")
    private String menuTextEng;

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("menu")
    private List<SubMenu> subMenus = new ArrayList<>();

    @Transient
    private String moduleName;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//    @JoinTable(name = "sec_role_menus", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "MENU_ID")})
//    private Set<SecurityRole> roles;

    public boolean hasId() {
        return menuId != null && menuId > 0;
    }

    public Menu() {
    }

    public Menu(Tuple tuple) {
        menuId = tuple.get("MENU_ID", Long.class);
        menuName = tuple.get("MENU_NAME", String.class);
        menuTextEng = tuple.get("MENU_TEXT_ENG", String.class);
        moduleName = tuple.get("MODULE_NAME", String.class);
        menuOrderNo = tuple.get("MENU_ORDER_NO", Integer.class);
        activeYn = tuple.get("ACTIVE_YN", String.class);
        baseUrl = tuple.get("BASE_URL", String.class);
        basePath = tuple.get("BASE_PATH", String.class);
        icon = tuple.get("ICON", String.class);
        bgColor = tuple.get("BG_COLOR", String.class);
        dashboardVisibleYn = tuple.get("DASHBOARD_VISIBLE_YN", String.class);
    }

}
