package com.momchilgenov.springboot.mvcweb.auth;

import com.momchilgenov.springboot.mvcweb.entity.User;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthenticationService {
    private final String URL_OF_JWT_AUTHENTICATOR;

    public AuthenticationService(@Value("${URL_OF_JWT_AUTHENTICATOR}") String URL_OF_JWT_AUTHENTICATOR) {
        this.URL_OF_JWT_AUTHENTICATOR = URL_OF_JWT_AUTHENTICATOR;
    }

    public JwtTokenPair authenticateUser(String username, String password) {
        String str = "This is a trivial public message";

        System.out.println("in backendservice");
        User user = new User(username, password);

        // Send the login credentials to the backend
        RestTemplate restTemplate = new RestTemplate();
        JwtTokenPair jwtTokenPair = restTemplate.postForObject(URL_OF_JWT_AUTHENTICATOR, user, JwtTokenPair.class);

        return jwtTokenPair;
    }

}
