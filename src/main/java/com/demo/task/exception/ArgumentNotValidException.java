package com.demo.task.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArgumentNotValidException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("field")
    private String field;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public ArgumentNotValidException(String message, String field) {
        super(message);
        this.message = message;
        this.statusCode = 99;
        this.field = field;
    }
    public ArgumentNotValidException(String message,  Integer statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
        this.field = null;
    }

    public ArgumentNotValidException(String message, Integer statusCode, String field) {
        super(message);
        this.message = message;
        this.field = field;
        this.statusCode = statusCode;
    }

    public ArgumentNotValidException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
