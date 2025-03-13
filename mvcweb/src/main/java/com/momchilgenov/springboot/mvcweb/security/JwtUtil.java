package com.momchilgenov.springboot.mvcweb.security;

import com.momchilgenov.springboot.mvcweb.exception.ExpiredJwtTokenException;
import com.momchilgenov.springboot.mvcweb.token.JwtClaimValidationStatus;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessTokenStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final String SECRET_KEY;
    private final String ISSUER;
    private final String AUDIENCE;
    private final String JWT_ACCESS_TOKEN = "accessToken";

    public JwtUtil(@Value("${SECRET_KEY}") String SECRET_KEY, @Value("${ISSUER}") String ISSUER,
                   @Value("${AUDIENCE}") String AUDIENCE) {
        this.SECRET_KEY = SECRET_KEY;
        this.ISSUER = ISSUER;
        this.AUDIENCE = AUDIENCE;
    }

    private boolean isIssuerValid(JwtAccessTokenStatus tokenStatus) {
        return tokenStatus.iss().equals(JwtClaimValidationStatus.VALID);
    }

    private boolean isAudienceValid(JwtAccessTokenStatus tokenStatus) {
        return tokenStatus.aud().equals(JwtClaimValidationStatus.VALID);
    }

    private boolean isExpired(JwtAccessTokenStatus tokenStatus){
        return tokenStatus.exp();
    }

    public String extractJwt(HttpServletRequest request) {
        String jwt;
        jwt = extractJwtFromCookies(request);
        if (jwt != null) {
            return jwt;
        }
        jwt = extractJwtFromAuthorizationHeader(request);
        return jwt;
    }

    public String extractJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); // Get all cookies from the request
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) { // Check if the cookie name matches
                    return cookie.getValue(); // Return the JWT from the cookie
                }
            }
        }
        return null; // Return null if no cookie is found
    }

    public String extractJwtFromAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix to get the token
        }
        return null;
    }

    public boolean validateToken(String token) throws ExpiredJwtTokenException {
        try {
            //verifies signature,checks that token hasn't expired
            //is properly formatted, is supported, is not null or empty
            Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return validateIssuer(token) && validateAudience(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException("JWT has expired", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateIssuer(String token) {
        String issuer = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token).getBody().getIssuer();
        return ISSUER.equals(issuer);
    }

    public boolean validateAudience(String token) {
        String audience = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token).getBody().getAudience();
        return AUDIENCE.equals(audience);
    }

    public List<GrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token).getBody();
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            return null;
        }
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
