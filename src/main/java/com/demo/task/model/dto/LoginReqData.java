package com.demo.task.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginReqData implements Serializable {
    private static final Long serialVersionUID = 5926468583005150707L;

    @NotBlank(message = "User ID CAN NOT BE BLANK!")
    private String userId;

    @NotBlank(message = "PASSWORD CAN NOT BE BLANK!")
    private String password;

}
