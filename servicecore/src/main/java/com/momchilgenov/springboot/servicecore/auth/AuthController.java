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

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenPair> login(@RequestBody User user) {

        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println("Received username = " + username + " and password = " + password);

        JwtTokenPair jwtTokenPair = authService.authenticateUser(user);

        return ResponseEntity.ok(jwtTokenPair);
    }

    @PostMapping("/validateAccessToken")
    public boolean validateAccessToken(){
        //todo - check user exists, roles match, not revoked
        return false;
    }

    @PostMapping("/validateRefreshToken")
    public boolean validateRefreshToken(){
        /*todo - check user exists, not revoked, refresh_token=true, audience, issuer, etc can be checked in mvcweb
           and iat is not BEFORE current timestamp for user, if it is, revoke token send fail
        */
        return false;
    }

    @PostMapping("/register")
    public boolean register(){
        //todo - do last, after domain is clear
        return false;
    }

    @PostMapping("/logout")
    public boolean logout(){
        //todo - revoke all sent tokens,update timestamp
        return false;
    }
}
