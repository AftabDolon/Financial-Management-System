package com.demo.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserListsDTO {
    private Long userId;
    private String username;
    private String userType;
    private String employeeName;
    private String departmentName;
    private String designationName;
    private String email;
    private String isActive;

}
