package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import com.momchilgenov.springboot.servicecore.security.JwtUtil;
import com.momchilgenov.springboot.servicecore.token.JwtAccessToken;
import com.momchilgenov.springboot.servicecore.token.JwtRefreshToken;
import com.momchilgenov.springboot.servicecore.token.JwtTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
