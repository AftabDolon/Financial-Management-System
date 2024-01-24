package com.demo.task.exception.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomErrorData {
    private static final Long serialVersionUID = 1L;

    @JsonProperty("message")
    private String message;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("status")
    private final boolean status = false;

    public CustomErrorData(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
