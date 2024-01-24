package com.demo.task.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnauthorizedUserException extends RuntimeException {
    public static final Long serialVersionUID = 1L;

    @JsonProperty("message")
    public String message;

    @JsonProperty("statusCode")
    public Integer statusCode;

    public UnauthorizedUserException(String message) {
        super(message);
        this.message = message;
        this.statusCode = 99;
    }

    public UnauthorizedUserException(String message, Integer statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
