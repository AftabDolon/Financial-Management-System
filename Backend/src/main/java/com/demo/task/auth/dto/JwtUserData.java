package com.demo.task.auth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtUserData implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long userId;
    private Long userPassword;
}
