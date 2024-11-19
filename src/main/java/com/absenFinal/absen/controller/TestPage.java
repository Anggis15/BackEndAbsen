package com.absenFinal.absen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/11/24 23.02
@Last Modified 16/11/24 23.02
Version 1.0
*/
@Controller
@RequestMapping("test")
public class TestPage {

    @GetMapping("/test")
    public String testPage(HttpServletRequest request){
        System.out.println("test");

        return "jamkerja";
    }
}
