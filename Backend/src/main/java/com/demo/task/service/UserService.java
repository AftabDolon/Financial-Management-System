package com.demo.task.service;

import com.demo.task.auth.dto.SecurityRole;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.model.Menu;
import com.demo.task.model.dto.UserListsDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface UserService extends BaseService<SecurityUser, Long> {
    Optional<SecurityUser> getDataByUser(String userName);

    SecurityRole saveOrUpdateRole(SecurityRole role, HttpServletRequest request);

    List<Menu> getAllMenuData();

    List<Menu> getMenusAndSubmenusByRoleId(HttpServletRequest request);

    List<UserListsDTO> getUserList(UserListsDTO userListsDTO);

    Map<String, Object> userDisplayProfile(HttpServletRequest request);
}
