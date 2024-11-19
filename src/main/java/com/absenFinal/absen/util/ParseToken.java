package com.absenFinal.absen.util;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 22.21
@Last Modified 11/11/24 22.21
Version 1.0
*/

import com.absenFinal.absen.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ParseToken {

    @Autowired
    private JwtUtility jwtUtility;

    public String getUsernameFromToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "": authorization;
        String token = null;
        String userName = null;
        if(!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length()>7)
        {
            token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
//                token = Crypto.performDecrypt(token);
            userName = jwtUtility.getUsernameFromToken(token);

        }
        return null;
    }


    public static double calculateWorkingHours(LocalDateTime checkIn, LocalDateTime checkOut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Parse the times from string to LocalTime
        LocalDateTime checkInTime = LocalDateTime.parse(checkIn.toString().replace("T"," "), formatter);
        LocalDateTime checkOutTime = LocalDateTime.parse(checkOut.toString().replace("T"," "), formatter);

        // Calculate the difference in minutes as a long
        long workingMinutes = ChronoUnit.MINUTES.between(checkInTime, checkOutTime);

        // Convert working minutes into hours (fractional) as a double
        return workingMinutes / 60.0; // Ensure result is a double
    }
}
