package com.momchilgenov.springboot.servicecore.security;

import com.momchilgenov.springboot.servicecore.User;
import com.momchilgenov.springboot.servicecore.token.JwtAccessToken;
import com.momchilgenov.springboot.servicecore.token.JwtRefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//generates jwt tokens
@Component
public class JwtUtil {
    private final String SECRET_KEY;
    private final String ISSUER;
    private final String AUDIENCE;
    private final int ACCESS_TOKEN_DURATION_IN_MINUTES;

    private final int REFRESH_TOKEN_DURATION_IN_MINUTES;

    public JwtUtil(@Value("${SECRET_KEY}") String SECRET_KEY, @Value("${ISSUER}") String ISSUER,
                   @Value("${AUDIENCE}") String AUDIENCE,
                   @Value("${ACCESS_TOKEN_DURATION_IN_MINUTES}") int ACCESS_TOKEN_DURATION_IN_MINUTES,
                   @Value("${REFRESH_TOKEN_DURATION_IN_MINUTES}") int REFRESH_TOKEN_DURATION_IN_MINUTES) {
        this.SECRET_KEY = SECRET_KEY;
        this.ISSUER = ISSUER;
        this.AUDIENCE = AUDIENCE;
        this.ACCESS_TOKEN_DURATION_IN_MINUTES = ACCESS_TOKEN_DURATION_IN_MINUTES;
        this.REFRESH_TOKEN_DURATION_IN_MINUTES = REFRESH_TOKEN_DURATION_IN_MINUTES;
    }

    public JwtAccessToken generateJwtAccessToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return new JwtAccessToken(
                Jwts.builder()
                        .setAudience(AUDIENCE)
                        .setIssuer(ISSUER)
                        .setIssuedAt(new Date())
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * ACCESS_TOKEN_DURATION_IN_MINUTES))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .addClaims(claims)
                        .compact()
        );
    }

    public JwtRefreshToken generateJwtRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        //jti and token_type = refresh
        claims.put("refresh_token", true);
        String jti = JtiGenerator.generateJti();
        claims.put("jti", jti);
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return new JwtRefreshToken(
                Jwts.builder()
                        .setAudience(AUDIENCE)
                        .setIssuer(ISSUER)
                        .setIssuedAt(new Date())
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * REFRESH_TOKEN_DURATION_IN_MINUTES))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .addClaims(claims)
                        .compact()
        );
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

    public boolean isExpired(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);  // This will throw if expired
            return false; // Token is valid
        } catch (ExpiredJwtException e) {
            return true; // Token is expired
        } catch (JwtException e) {
            // Handles other types of JWT exceptions (signature invalid, malformed token)
            return true; // Treat it as expired or invalid
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token).getBody().getSubject();
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
