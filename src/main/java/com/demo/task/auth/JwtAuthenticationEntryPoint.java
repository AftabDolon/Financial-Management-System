package com.demo.task.auth;

import com.demo.task.constant.ErrorStatusCode;
import com.demo.task.exception.data.CustomErrorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final Long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Object filterError = request.getAttribute("CUSTOM-FILTER-ERROR");
        final String errorMsg = filterError != null ? (String) filterError : null;

        if (errorMsg != null && !response.isCommitted()) {
            CustomErrorData customErrorData = null;
            if (errorMsg.contains(ErrorStatusCode.JWT_TOKEN_EXPIRED.getValue())) {
                customErrorData = new CustomErrorData(ErrorStatusCode.JWT_TOKEN_EXPIRED.getValue(),
                        ErrorStatusCode.JWT_TOKEN_EXPIRED.getCode());
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            OutputStream responseStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(responseStream, customErrorData);
            responseStream.flush();
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
        }
    }
}
