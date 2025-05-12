package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import com.momchilgenov.springboot.servicecore.security.JwtUtil;
import com.momchilgenov.springboot.servicecore.token.JwtAccessToken;
import com.momchilgenov.springboot.servicecore.token.JwtRefreshToken;
import com.momchilgenov.springboot.servicecore.token.JwtTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final String DEFAULT_ROLE = "ROLE_USER"; //sets default role to newly registered users

    @Autowired
    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil,
                       TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtTokenPair authenticateUser(User user) {

        User userDto = authRepository.authenticateUser(user);
        if (userDto == null) {
            //todo - throw userNotFoundException, catch in controller, send 404
            return null;
        }
        String rawPassword = user.getPassword();
        String hashedPassword = userDto.getPassword();
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            return null;
        }
        tokenService.revokeAll(userDto.getUsername());
        JwtAccessToken accessToken = jwtUtil.generateJwtAccessToken(userDto);
        JwtRefreshToken refreshToken = jwtUtil.generateJwtRefreshToken(userDto);
        return new JwtTokenPair(accessToken, refreshToken);
    }

    public JwtAccessTokenStatus validateAccessToken(JwtAccessToken token) {
        JwtClaimValidationStatus issuer;
        JwtClaimValidationStatus audience;
        boolean isExpired;
        String accessToken = token.token();
        String username = jwtUtil.getUsernameFromToken(accessToken);
        Date iatClaim = jwtUtil.getIssuedAt(accessToken);
        if (tokenService.isRevoked(username, iatClaim)) {
            System.out.println("Revoked token found.");
            return new JwtAccessTokenStatus(JwtClaimValidationStatus.INVALID, JwtClaimValidationStatus.INVALID,
                    true, JwtClaimValidationStatus.BANNED, false);
        }

        if (jwtUtil.validateIssuer(accessToken)) {
            issuer = JwtClaimValidationStatus.VALID;
        } else {
            issuer = JwtClaimValidationStatus.INVALID;
        }

        if (jwtUtil.validateAudience(accessToken)) {
            audience = JwtClaimValidationStatus.VALID;
        } else {
            audience = JwtClaimValidationStatus.INVALID;
        }
        isExpired = jwtUtil.isExpired(token.token());

        List<String> roles = jwtUtil.getRolesFromToken(accessToken);

        var statusDto = authRepository.validateSubjectExistsAndRolesMatch(username, roles);
        JwtClaimValidationStatus subjectStatus;
        if (statusDto.sub() == null) {
            subjectStatus = JwtClaimValidationStatus.NOT_FOUND;
        } else {
            subjectStatus = statusDto.sub();
        }

        return new JwtAccessTokenStatus(issuer, audience, isExpired, subjectStatus, statusDto.sub_roles_match());
    }

    public JwtTokenPair validateRefreshToken(JwtRefreshToken token) {
        if (!jwtUtil.validateIssuer(token.token())) {
            System.out.println("Refresh token has invalid issuer");
            return null;
        }
        if (!jwtUtil.validateAudience(token.token())) {
            System.out.println("Refresh token has invalid audience");
            return null;
        }
        if (!jwtUtil.isRefreshToken(token.token())) {
            System.out.println("Token is not a refresh token.");
            return null;
        }
        String username = jwtUtil.getUsernameFromToken(token.token());
        User userDto = authRepository.findUserByUsername(username);
        if (userDto == null) {
            return null;
        }
        Date iatClaim = jwtUtil.getIssuedAt(token.token());
        if (tokenService.isRevoked(username, iatClaim)) {
            System.out.println("Refresh token is revoked");
            return null;
        }
        tokenService.revokeAll(username);
        JwtRefreshToken refreshToken = jwtUtil.generateJwtRefreshToken(token.token());
        System.out.println("Received refresh token was = " + token.token());
        System.out.println("Generated refresh token is = " + refreshToken.token());
        //null if expired
        if (refreshToken == null) {
            System.out.println("Refresh token has expired");
            return null;
        }
        JwtAccessToken accessToken = jwtUtil.generateJwtAccessToken(userDto);

        return new JwtTokenPair(accessToken, refreshToken);
    }


    public void logout(String username) {
        tokenService.revokeAll(username);
    }

    public UserRegistrationStatus register(User user) {
        User userExists = authRepository.findUserByUsername(user.getUsername());
        if (userExists != null) {
            return new UserRegistrationStatus(true, null);
        }
        //hash the user's password
        String rawPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);

        //gives the user a default role upon registration
        List<String> roles = new ArrayList<>();
        roles.add(DEFAULT_ROLE);
        user.setRoles(roles);
        authRepository.registerUser(user);
        tokenService.revokeAll(user.getUsername());
        JwtAccessToken accessToken = jwtUtil.generateJwtAccessToken(user);
        JwtRefreshToken refreshToken = jwtUtil.generateJwtRefreshToken(user);
        JwtTokenPair tokenPair = new JwtTokenPair(accessToken, refreshToken);
        return new UserRegistrationStatus(false, tokenPair);
    }
}
