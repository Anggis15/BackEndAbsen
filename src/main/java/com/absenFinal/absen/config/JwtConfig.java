package com.absenFinal.absen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/10/24 22.44
@Last Modified 16/10/24 22.44
Version 1.0
*/
@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {
    private static String jwtExpiration;
    private static String jwtSecret;


    public static String getJwtExpiration() {
        return jwtExpiration;
    }

    @Value("jwt.expiration")
    private void setJwtExpiration(String jwtExpiration) {
        JwtConfig.jwtExpiration = jwtExpiration;
    }

    public static String getJwtSecret() {
        return jwtSecret;
    }

    @Value("jwt.secret")
    private void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}
