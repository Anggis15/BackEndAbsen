package com.absenFinal.absen.controller;

import com.absenFinal.absen.dto.validasi.LoginDTO;
import com.absenFinal.absen.dto.validasi.RegisDTO;
import com.absenFinal.absen.model.User;
import com.absenFinal.absen.service.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/10/24 23.35
@Last Modified 16/10/24 23.35
Version 1.0
*/
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    UserDetailService userDetailService;

    @PostMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO, HttpServletRequest request) {
        User user = userDetailService.convertToEntity(loginDTO);
        return userDetailService.doLogin(user, request);
    }

    @PostMapping("/v1/doregis")
    public ResponseEntity<Object> doRegis(@Valid @RequestBody RegisDTO regisDTO, HttpServletRequest request){
        User user = userDetailService.convertToEntity(regisDTO);
        return userDetailService.doRegis(user,request);
    }
}
