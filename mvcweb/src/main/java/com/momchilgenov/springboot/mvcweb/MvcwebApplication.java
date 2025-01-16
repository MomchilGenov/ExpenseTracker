package com.momchilgenov.springboot.mvcweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class MvcwebApplication {

    //todo
   /*
    Implement a dummy jwt token, test it passes jwtUtil functions
    Save the jwt itself as a cookie
    Check for a cookie on jwtauthfilter

   */
    public static void main(String[] args) {

        SpringApplication.run(MvcwebApplication.class, args);
    }



}



