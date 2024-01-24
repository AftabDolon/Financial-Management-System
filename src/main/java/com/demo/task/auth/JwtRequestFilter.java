package com.demo.task.auth;

import com.demo.task.auth.dto.JwtUserData;
import com.demo.task.constant.ErrorStatusCode;
import com.demo.task.exception.UnauthorizedUserException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = null;
        if (!request.getRequestURI().equals("/TASK-PROJECT/user/login")) {
            requestTokenHeader = request.getHeader("Authorization");
        }
        String username = null;
        String jwtToken = null;
        JwtUserData jwtUserData = null;
        if (requestTokenHeader == null) {
            requestTokenHeader = request.getHeader("token");
        }
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtils.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                throw new UnauthorizedUserException("UNABLE TO GET JWT TOKEN", 99);
            } catch (ExpiredJwtException e) {
//            //  System.out.println("JWT TOKEN HAS EXPIRED");
                request.setAttribute("CUSTOM-FILTER-ERROR", ErrorStatusCode.JWT_TOKEN_EXPIRED.getValue());
                throw new UnauthorizedUserException("JWT TOKEN HAS EXPIRED", 99);
            }
        } else {
            System.out.println("JWT Token does not start with Bearer");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
