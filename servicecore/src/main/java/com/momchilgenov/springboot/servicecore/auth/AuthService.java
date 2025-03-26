package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import com.momchilgenov.springboot.servicecore.security.JwtUtil;
import com.momchilgenov.springboot.servicecore.token.JwtAccessToken;
import com.momchilgenov.springboot.servicecore.token.JwtRefreshToken;
import com.momchilgenov.springboot.servicecore.token.JwtTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil, TokenService tokenService) {
        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    public JwtTokenPair authenticateUser(User user) {

        //validates user exists in db and password matches
        User userDto = authRepository.authenticateUser(user);
        if (userDto == null) {
            //todo - throw userNotFoundException, catch in controller, send 404
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
        JwtClaimValidationStatus subject;
        JwtClaimValidationStatus subject_matches_roles;
        boolean isExpired;
        String accessToken = token.token();
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

        String username = jwtUtil.getUsernameFromToken(accessToken);
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
        String username = jwtUtil.getUsernameFromToken(token.token());
        //todo - check roles match, check if token is revoked, validate token(refresh_token=true, audience, issuer)
        User userDto = authRepository.findUserByUsername(username);

        JwtRefreshToken refreshToken = jwtUtil.generateJwtRefreshToken(token.token());
        //null if expired
        if (refreshToken == null) {
            return null;
        }
        JwtAccessToken accessToken = jwtUtil.generateJwtAccessToken(userDto);
        return new JwtTokenPair(accessToken, refreshToken);
    }

}
