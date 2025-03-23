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
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil) {
        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
    }

    public JwtTokenPair authenticateUser(User user) {

        //validates user exists in db and password matches
        User userDto = authRepository.authenticateUser(user);
        if (userDto == null) {
            //todo - throw userNotFoundException, catch in controller, send 404
            return null;
        }

        //todo - (for token repo revocation logic) - save the token/jti in the repo upon creation/logout to keep track of revoked
        //and blacklisted tokens
        //todo keep track of last issued token timestamp to blacklist refresh tokens
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


    /*todo - check user exists, not revoked, refresh_token=true, audience, issuer, etc
           and iat is not BEFORE current timestamp for user, if it is, revoke token send null
    */
    public JwtTokenPair validateRefreshToken(JwtRefreshToken token) {
        //todo - (for token repo revocation logic) - save the token/jti in the repo upon creation/logout to keep track of revoked
        //and blacklisted tokens
        //todo keep track of last issued token timestamp to blacklist refresh tokens
        String username = jwtUtil.getUsernameFromToken(token.token());
        User userDto = authRepository.findUserByUsername(username);
        JwtAccessToken accessToken = jwtUtil.generateJwtAccessToken(userDto);
        JwtRefreshToken refreshToken = jwtUtil.generateJwtRefreshToken(userDto);
        return new JwtTokenPair(accessToken, refreshToken);
    }

}
