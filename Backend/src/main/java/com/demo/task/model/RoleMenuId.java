package com.demo.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class RoleMenuId implements Serializable {

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "MENU_ID")
    private Long menuId;

    public RoleMenuId() {

    }

    public RoleMenuId(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
