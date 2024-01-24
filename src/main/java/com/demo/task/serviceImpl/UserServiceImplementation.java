package com.demo.task.serviceImpl;

import com.demo.task.auth.JwtTokenUtils;
import com.demo.task.auth.dto.SecurityRole;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.constant.ErrorStatusCode;
import com.demo.task.exception.InvalidOperationException;
import com.demo.task.model.Menu;
import com.demo.task.model.SubMenu;
import com.demo.task.model.dto.UserListsDTO;
import com.demo.task.repository.*;
import com.demo.task.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private SecurityUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private SubMenuRepository subMenuRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;


    @Override
    public SecurityUser save(SecurityUser user, HttpServletRequest request) {
        user.setInsertBy(String.valueOf(getUserId(request)));
        if (user.getUserTypeId() == 1) {
            user.setUsername(user.getEmpId());
        }
        user.setUserPass(getEncodedPassword(user.getUserPass()));
        return userRepository.save(user);
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public SecurityUser update(SecurityUser user, HttpServletRequest request) throws Exception {
        if (!user.hasId()) {
            throw new InvalidOperationException(ErrorStatusCode.NOT_PROVIDE_ID.getValue(), ErrorStatusCode.NOT_PROVIDE_ID.getCode());
        } else if (Stream.of(user.getEmail(), user.getIsActive()).anyMatch(property -> property != null)) {
            return save(user, request);
        } else {
            throw new InvalidOperationException("Mandatory value cannot be null", 417);
        }
    }

    @Override
    public void deleteByIds(Long ids) {
        userRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public List<SecurityUser> getDataByIds(Long... ids) {
        return userRepository.getDataByIds(Arrays.asList(ids));
    }

    @Override
    public List<SecurityUser> getData() {
        return userRepository.findAll();
    }

    public List<UserListsDTO> getUserList(UserListsDTO userListsDTO) {
        List<Object[]> userList = userRepository.getUserList(userListsDTO.getUserType());
        return userList.stream().map(row -> new UserListsDTO((Long) row[0], (String) row[1], (String) row[2], (String) row[3], (String) row[4], (String) row[5], (String) row[6], (String) row[7]))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> userDisplayProfile(HttpServletRequest request) {
        return userRepository.getUserDisplayProfile(getUserId(request));
    }

    @Override
    public Optional<SecurityUser> getDataByUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    public Long getUserId(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtils.getUserNameFromToken(jwtToken);
        SecurityUser userValue = userRepository.findByUsername(username).get();
        return userValue.getUserId();
    }

    @Override
    public SecurityRole saveOrUpdateRole(SecurityRole role, HttpServletRequest request) {
        if (role.getDmlTypeId() == 2 && !role.hasId()) {
            throw new InvalidOperationException(ErrorStatusCode.NOT_PROVIDE_ID.getValue(), ErrorStatusCode.NOT_PROVIDE_ID.getCode());
        } else {
            if (Stream.of(role.getRoleName(), role.getRoleKey(), role.getActiveYn()).anyMatch(property -> property != null)) {
                role.setInsertBy(String.valueOf(getUserId(request)));
                return roleRepository.save(role);
            } else {
                throw new InvalidOperationException("Mandatory fields missing ", 417);
            }
        }
    }

    @Override
    public List<Menu> getAllMenuData() {
        return menuRepository.findAllMenu();
    }

    @Override
    public List<Menu> getMenusAndSubmenusByRoleId(HttpServletRequest request) {
        List<Long> roleId = userRepository.findRoleId(getUserId(request));
        List<Menu> menus = menuRepository.findMenusByRoleIds(roleId);
        for (Menu menu : menus) {
            List<SubMenu> subMenus = new ArrayList<>();
            String submenusString = roleMenuRepository.findSubmenusByRoleIdAndMenuId(roleId, menu.getMenuId());

            if (submenusString != null && !submenusString.isEmpty()) {
                List<Long> submenuIds = Arrays.stream(submenusString.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                subMenus = subMenuRepository.findSubmenusByIds(submenuIds);
            }
            menu.setSubMenus(subMenus);
        }

        return menus;
    }
}
