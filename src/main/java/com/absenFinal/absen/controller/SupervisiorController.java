package com.absenFinal.absen.controller;

import com.absenFinal.absen.service.SupervisiorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 20/11/24 01.13
@Last Modified 20/11/24 01.13
Version 1.0
*/
@RestController
@RequestMapping("api/supervisior")
public class SupervisiorController {
    @Autowired
    SupervisiorService supervisiorService;

    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable =  PageRequest.of(0,10,
                Sort.by("createdAt"));
        return supervisiorService.findAll(pageable, request);
    }
}
