package com.demo.task.auth.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SEC_ROLE")
public class SecurityRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", unique = true, nullable = false)
    private Long roleId;
    @NotBlank(message = "ROLE NAME CAN NOT BE BLANK!")
    @Column(name = "ROLE_NAME")
    private String roleName;
    @NotBlank(message = "ACTIVE_YN MUST BE SELECTED ONE!")
    @Column(name = "ACTIVE_YN")
    private String activeYn;
    @Column(name = "INSERT_BY")
    private String insertBy;
    @Column(name = "INSERT_DATE")
    private String insertDate;
    @Column(name = "UPDATE_BY")
    private String updateBy;
    @Column(name = "UPDATE_DATE")
    private String updateDate;
    @NotBlank(message = "ROLE KEY CAN NOT BE BLANK!")
    @Column(name = "ROLE_KEY")
    private String roleKey;
    @Column(name = "GRANT_ALL_YN")
    private String grantAllYn;

    @Transient
    private Integer dmlTypeId;

    public boolean hasId() {
        return roleId != null && roleId > 0;
    }

}
