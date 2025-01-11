package com.momchilgenov.springboot.mvcweb.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BackendService {

    private final String URL_OF_JWT_AUTHENTICATOR = "enter valid ip address here";

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("URL_OF_JWT_AUTHENTICATOR") // Set a default base URL if needed
                .build();
    }


}
