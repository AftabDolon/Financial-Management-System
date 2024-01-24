package com.demo.task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponseSuccessData {
    @JsonProperty("token")
    private String token;
    @JsonProperty("status")
    private int status;

//    public JwtResponseSuccessData(String message, String token, T data) {
//        super(true, 200, message, data);
//        this.token = token;
//    }
}
