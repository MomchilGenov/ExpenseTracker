package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import com.momchilgenov.springboot.servicecore.token.JwtAccessToken;
import com.momchilgenov.springboot.servicecore.token.JwtRefreshToken;
import com.momchilgenov.springboot.servicecore.token.JwtTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenPair> login(@RequestBody User user) {

        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println("Received username = " + username + " and password = " + password);

        JwtAccessToken accessToken = new JwtAccessToken("Dummy access token"); //todo - implement generation with helper class
        JwtRefreshToken refreshToken = new JwtRefreshToken("Dummy refresh token"); //todo - implement generation with helper class
        JwtTokenPair jwtTokenPair = new JwtTokenPair(accessToken, refreshToken);
        //TODO
        /*

        5. Develop jwt generation of the 2 tokens
         */
        return ResponseEntity.ok(jwtTokenPair);
    }
}
