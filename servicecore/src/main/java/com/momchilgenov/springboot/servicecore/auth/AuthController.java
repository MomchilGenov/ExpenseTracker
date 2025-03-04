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
}
