package com.demo.task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBaseData<T> {
    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("status_code")
    private Integer statusCode;

//    @JsonProperty("message")
//    private String message;

    @JsonProperty("data")
    private T data;

}
