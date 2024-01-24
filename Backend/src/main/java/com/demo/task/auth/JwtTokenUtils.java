package com.demo.task.auth;

import com.demo.task.auth.dto.JwtUserData;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.exception.UnauthorizedUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils implements Serializable {
    private static final Long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_SHORTLY = 60;
    public static final int TOKEN_EXPIRE_TIME_11PM = 23;

    public static final String USER_ID = "userId";

    @Value("${jwt.secret}")
    private String secret;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public JwtUserData getJwtUserDataFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        JwtUserData jwtUserData;
        if (claims.get("userId") != null) {
            jwtUserData = new JwtUserData();
            jwtUserData.setUserId(claims.get(USER_ID) != null ? Long.parseLong(claims.get(USER_ID).toString()) : null);
        } else {
            throw new UnauthorizedUserException("Invalid JWT token", 99);
        }
        return jwtUserData;
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(USER_ID, userDetails.getUsername());
    return doGenerateToken(claims, userDetails.getUsername());
}

    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userId);
        return doGenerateToken(claims, userId);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTokenExpireDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Date getTokenExpireDate() {
        Date expireDate;
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (currentHour > 22) {
            // current date is after 10pm expire date will be next day 11:59PM
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_YEAR, 1); // Add 1 day to the current date
            calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, now.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        }
        calendar.set(Calendar.HOUR_OF_DAY, TOKEN_EXPIRE_TIME_11PM);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        expireDate = calendar.getTime();
        return expireDate;
    }

    private String doGenerateIssueAccessToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_SHORTLY * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public Boolean validateToken(JwtUserData jwtUserData, String token, SecurityUser userDetails) {
        return (jwtUserData != null && jwtUserData.getUserId() != null
                && jwtUserData.getUserId().equals(userDetails.getUserId())
                && !isTokenExpired(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
