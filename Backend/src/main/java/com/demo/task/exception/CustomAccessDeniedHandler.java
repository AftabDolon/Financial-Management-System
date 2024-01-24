package com.demo.task.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        writeCustomResponse(response);
    }

    private void writeCustomResponse(HttpServletResponse response) {
        if (!response.isCommitted()) {
            try {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("{\"status\":false," +
                        "\"code\":99," +
                        "\"message\":\"Access Denied\"}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
