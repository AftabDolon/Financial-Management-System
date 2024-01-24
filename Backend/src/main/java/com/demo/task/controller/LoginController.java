package com.demo.task.controller;

import com.demo.task.constant.WebApiUrlConstants;
import com.demo.task.model.dto.LoginReqData;
import com.demo.task.response.JwtResponseSuccessData;
import com.demo.task.serviceImpl.UserProfileDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserProfileDetailsService jwtService;


    @RequestMapping(value = WebApiUrlConstants.USER_LOGIN_API, method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqData loginReqData, HttpServletRequest request) throws Exception {
        JwtResponseSuccessData response = jwtService.createJwtToken(loginReqData, request);
        return ResponseEntity.ok(response);
    }

//    @RequestMapping(value = WebApiUrlConstants.USER_LOGOUT_API, method = RequestMethod.GET)
//    public ResponseEntity<?> logOut(HttpServletRequest request) {
//        ProcedureRespDetails procedureRespDetails = jwtService.saveLoginHistory(request, 2);
//        return ResponseEntity.ok(procedureRespDetails);
//    }
}
