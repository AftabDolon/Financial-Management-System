package com.demo.task.controller;

import com.demo.task.auth.dto.SecurityRole;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.constant.WebApiUrlConstants;
import com.demo.task.exception.InvalidOperationException;
import com.demo.task.model.Menu;
import com.demo.task.model.dto.UserListsDTO;
import com.demo.task.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = WebApiUrlConstants.USER_SAVE_DATA_API, method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@Valid @RequestBody SecurityUser user, HttpServletRequest request) {
        try {
            userService.save(user, request);
            return ResponseEntity.status(HttpStatus.OK).body("RECORD INSERTED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @RequestMapping(value = WebApiUrlConstants.USER_UPDATE_DATA_API, method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@Valid @RequestBody SecurityUser user, HttpServletRequest request) {
        try {
            userService.update(user, request);
            return ResponseEntity.ok("RECORD UPDATED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


    @RequestMapping(value = WebApiUrlConstants.USER_API + "/user-listValue", method = RequestMethod.POST)
    public ResponseEntity<?> getUserList(@RequestBody UserListsDTO userListsDTO) {
        return ResponseEntity.ok(userService.getUserList(userListsDTO));
    }

//    @RequestMapping(value = WebApiUrlConstants.USER_API + "/single-user-value/{userId}", method = RequestMethod.GET)
//    public ResponseEntity<?> getSingleUser(@PathVariable("userId") String userId) {
//        return ResponseEntity.ok(userProfileService.getSingleUser(userId));
//    }
//

    @RequestMapping(value = WebApiUrlConstants.USER_ROLE_SAVE_API, method = RequestMethod.POST)
    public ResponseEntity<?> saveRole(@Valid @RequestBody SecurityRole role, HttpServletRequest request) {
        try {
            role.setDmlTypeId(1);
            userService.saveOrUpdateRole(role, request);
            return ResponseEntity.ok("RECORD INSERTED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


    @RequestMapping(value = WebApiUrlConstants.USER_ROLE_UPDATE_API, method = RequestMethod.POST)
    public ResponseEntity<?> updateRole(@Valid @RequestBody SecurityRole role, HttpServletRequest request) {
        try {
            role.setDmlTypeId(2);
            userService.saveOrUpdateRole(role, request);
            return ResponseEntity.ok("RECORD UPDATED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @RequestMapping(value = WebApiUrlConstants.MENU_API, method = RequestMethod.GET)
    public List<Menu> getMenuList(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_SUPER_ADMIN")) {
            return userService.getAllMenuData();
        } else {
            return userService.getMenusAndSubmenusByRoleId(request);
        }
    }


    //    @RequestMapping(value = WebApiUrlConstants.USER_LIST_SINGLE_API, method = RequestMethod.GET)
//    public List<UserValueDTO> getUserIdsInfo() {
//        return userProfileService.getUserIds();
//    }
//
//
//    @RequestMapping(value = WebApiUrlConstants.USER_PROFILE_API, method = RequestMethod.GET)
//    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
//        return ResponseEntity.ok(userProfileService.getUserProfileInfo(request));
//    }
//
//    @RequestMapping(value = WebApiUrlConstants.USER_PROFILE_DISPLAY_API, method = RequestMethod.GET)
//    public ResponseEntity<?> UserDisplayProfile(HttpServletRequest request) {
//        return ResponseEntity.ok(userService.userDisplayProfile(request));
//    }


}
