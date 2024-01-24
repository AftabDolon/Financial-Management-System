package com.demo.task.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "SEC_ROLE_MENUS")
public class RoleMenu implements Serializable {

    @EmbeddedId
    private RoleMenuId id;

    @Column(name = "ACTIVE_YN")
    private String activeYn;

    @Column(name = "INSERT_BY")
    private Long insertBy;

    @Column(name = "INSERT_DATE")
    private String insertDate;

    @Column(name = "SUBMENUS")
    private String submenus;

    @Transient
    private List<Long> submenuIds;


    @Transient
    private Long menuId;

    public RoleMenu() {
        // Default constructor
    }

    public RoleMenu(Long roleId, Long menuId, List<Long> submenuIds) {
        this.id = new RoleMenuId(roleId, menuId);
        this.setSubmenuIds(submenuIds);
    }

    public List<Long> getSubmenuIds() {
        if (submenus == null || submenus.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(submenus.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public void setSubmenuIds(List<Long> submenuIds) {
        this.submenuIds = submenuIds;
        this.submenus = submenuIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public RoleMenu(Tuple tuple) {
        menuId = tuple.get("MENU_ID", Long.class);
        activeYn = tuple.get("ACTIVE_YN", String.class);
        submenus = tuple.get("SUBMENUS", String.class);
    }
}
