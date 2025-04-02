package com.momchilgenov.springboot.mvcweb;

import com.momchilgenov.springboot.mvcweb.entity.User;
import com.momchilgenov.springboot.mvcweb.security.JwtUtil;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessTokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class MvcwebApplication {


    public static void main(String[] args) {

        SpringApplication.run(MvcwebApplication.class, args);
    }
}



