package com.momchilgenov.springboot.mvcweb.security;

import com.momchilgenov.springboot.mvcweb.auth.AuthenticationService;
import com.momchilgenov.springboot.mvcweb.exception.ExpiredJwtTokenException;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessTokenStatus;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtRefreshToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    private final AuthenticationService authenticationService;

    @Autowired
    public JwtAuthFilter(JwtUtil util, AuthenticationService service) {
        this.jwtUtil = util;
        this.authenticationService = service;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("in JwtAuthFilter");
        String jwt = jwtUtil.extractJwt(request);
        try {
            if (jwt != null) {
                JwtAccessTokenStatus tokenStatus = authenticationService.validateAccessToken(new JwtAccessToken(jwt));
                if (jwtUtil.isValid(tokenStatus)) {
                    if (!jwtUtil.isExpired(tokenStatus)) {
                        //valid and not expired, call upper code

                        System.out.println("Received a jwt in filter");
                        String username = jwtUtil.getUsernameFromToken(jwt);

                        // Create an authentication token to access in custom authentication provider
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                username, null, jwtUtil.getRolesFromToken(jwt));
                        //populates the authentication object with more details from the request object
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        System.out.println("Token is valid, but expired, attempting a refresh");
                        String refreshToken = jwtUtil.extractJwtRefreshTokenFromCookies(request);
                        if (refreshToken == null) {
                            System.out.println("No refresh token found in cookies.");
                        } else {
                            System.out.println("In filter, found a refresh token = " + refreshToken);
                            //if token is invalid or expired, returns null
                            JwtTokenPair tokenPair = authenticationService.validateRefreshToken(
                                    new JwtRefreshToken(refreshToken));
                            if (tokenPair == null) {
                                System.out.println("Refresh token either expired or invalid");
                            } else {
                                String username = jwtUtil.getUsernameFromToken(tokenPair.accessToken().token());

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        username, null, jwtUtil.getRolesFromToken(tokenPair.accessToken().token()));

                                //populates the authentication object with more details from the request object
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authentication);

                                Cookie accessTokenCookie = new Cookie("accessToken", tokenPair.accessToken().token());
                                accessTokenCookie.setHttpOnly(true);
                                accessTokenCookie.setPath("/");
                                response.addCookie(accessTokenCookie);

                                Cookie refreshTokenCookie = new Cookie("refreshToken", tokenPair.refreshToken().token());
                                refreshTokenCookie.setHttpOnly(true);
                                refreshTokenCookie.setPath("/");
                                response.addCookie(refreshTokenCookie);
                            }
                        }
                    }
                } else {
                    /*
                     * we don't check for refresh tokens on the assumption that an invalid access token is
                     * a malicious entity attempting unauthorized access
                     */
                    System.out.println("No valid tokens found.");
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("JWT is invalid!");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token!Please login again.");
            throw new RuntimeException("this is the problem", e);

        }

    }
}

