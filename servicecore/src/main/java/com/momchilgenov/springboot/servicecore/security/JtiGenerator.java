package com.momchilgenov.springboot.servicecore.security;

import java.util.UUID;

public class JtiGenerator {

    public static String generateJti() {
        return UUID.randomUUID().toString();
    }
}
