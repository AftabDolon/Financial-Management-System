package com.demo.task.serviceImpl;

import com.demo.task.auth.JwtTokenUtils;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.constant.ErrorStatusCode;
import com.demo.task.exception.ArgumentNotValidException;
import com.demo.task.model.dto.LoginReqData;
import com.demo.task.repository.SecurityUserRepository;
import com.demo.task.response.JwtResponseSuccessData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserProfileDetailsService extends SecurityUser implements UserDetailsService, Serializable {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public JwtResponseSuccessData createJwtToken(LoginReqData loginReqData, HttpServletRequest request) throws Exception {
        String username = loginReqData.getUserId();
        String userPassword = loginReqData.getPassword();
        authenticate(username, userPassword);
        UserDetails userDetails = loadUserByUsername(username);
        String newGenerateToken = jwtTokenUtils.generateToken(userDetails);
//        SecurityUser user = securityUserRepository.findByUsername(username).get();
//        if (user != null) {
//            user.setLastAccessIp(request.getRemoteAddr());
//            userDao.saveLoginHistory(user, 1);
//        }
        return new JwtResponseSuccessData(newGenerateToken, 200);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityUser> userOptional = securityUserRepository.findByUsername(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(ErrorStatusCode.INVALID_USER.getValue() + ": " + username);
        }

        SecurityUser user = userOptional.get();

        if (!user.getIsActive().equals("Y")) {
            throw new DisabledException(ErrorStatusCode.USER_NOT_ACTIVE.getValue());
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserPass(), getAuthority(user));
    }

    private Set getAuthority(SecurityUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()));
        });
        return authorities;
    }

    private void authenticate(String username, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userPassword));
        } catch (DisabledException e) {
            throw new ArgumentNotValidException(ErrorStatusCode.USER_NOT_ACTIVE.getValue(), ErrorStatusCode.USER_NOT_ACTIVE.getCode());
        } catch (BadCredentialsException e) {
            throw new ArgumentNotValidException(ErrorStatusCode.INVALID_USER_PASSWORD.getValue(), ErrorStatusCode.INVALID_USER_PASSWORD.getCode());
        }
    }

}
