package com.demo.task.auth.dto;

import com.demo.task.model.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "USERS")
public class SecurityUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false, unique = true)
    private Long userId;
    @Column(name = "EMP_ID")
    private String empId;
    @Column(name = "BLOCKED_FOR")
    private String blockedFor;
    @Column(name = "BLOCKED_ON")
    private String blockedOn;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "INSERT_BY")
    private String insertBy;
    @Column(name = "INSERT_DATE")
    private String insertDate;
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @Column(name = "IS_BLOCKED")
    private String isBlocked;
    @Column(name = "LAST_ACCESS_IP")
    private String lastAccessIp;
    @Column(name = "LAST_LOGGEDIN_ON")
    private String lastLoggdinOn;
    @Column(name = "LAST_LOGGOUT_ON")
    private String lastLogoutOn;
    @Column(name = "NEED_PASS_RESET")
    private String needPassReset;
    @Column(name = "OTP")
    private String otp;
    @Column(name = "OTP_EXPIRED_AT")
    private String otpExpiredAt;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "USER_NAME")
    private String username;
    @Column(name = "USER_PASS")
    private String userPass;
    @Column(name = "USER_TYPE_ID")
    private Long userTypeId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "SEC_USER_ROLES", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<SecurityRole> roles;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//    private Employee employee;

    public boolean hasId() {
        return userId != null && userId > 0;
    }

    public SecurityUser() {
    }

    public SecurityUser(SecurityUser user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.userPass = user.getUserPass();
        this.isActive = user.getIsActive();
        this.roles = user.getRoles();
        this.userTypeId = user.getUserTypeId();
        this.lastLoggdinOn = user.getLastLoggdinOn();
    }

}
