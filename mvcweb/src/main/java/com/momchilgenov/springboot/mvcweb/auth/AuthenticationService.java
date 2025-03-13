package com.momchilgenov.springboot.mvcweb.auth;

import com.momchilgenov.springboot.mvcweb.entity.User;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessTokenStatus;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    private final String URL_OF_JWT_AUTHENTICATOR;
    private final String URL_OF_JWT_ACCESS_TOKEN_VALIDATION;

    public AuthenticationService(@Value("${URL_OF_JWT_AUTHENTICATOR}") String URL_OF_JWT_AUTHENTICATOR,
                                 @Value("${URL_OF_JWT_ACCESS_TOKEN_VALIDATION}")
                                 String URL_OF_JWT_ACCESS_TOKEN_VALIDATION) {
        this.URL_OF_JWT_AUTHENTICATOR = URL_OF_JWT_AUTHENTICATOR;
        this.URL_OF_JWT_ACCESS_TOKEN_VALIDATION = URL_OF_JWT_ACCESS_TOKEN_VALIDATION;
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


    public JwtAccessTokenStatus validateAccessToken(JwtAccessToken token) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(URL_OF_JWT_ACCESS_TOKEN_VALIDATION,
                token, JwtAccessTokenStatus.class);
    }

}
