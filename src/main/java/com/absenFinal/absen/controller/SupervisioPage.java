package com.absenFinal.absen.controller;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 17/11/24 14.25
@Last Modified 17/11/24 14.25
Version 1.0
*/

import com.absenFinal.absen.config.MainConfig;
import com.absenFinal.absen.dto.validasi.RegisEmployeeDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class SupervisioPage {

    @Autowired
    MainConfig mainConfig;

    @GetMapping("/daftar-servisior")
    public String getRegisterSupervisior(){
        return "menu/daftarEmployee";
    }
    @PostMapping("/daftar-servisior")
    public String registerSupervisior(HttpServletRequest request, Model model, @ModelAttribute("regisEmployee")RegisEmployeeDTO regisEmployeeDTO){
        String port = mainConfig.port;
        String ip = mainConfig.ip;;

        return "menu/daftarEmployee";
    }
}
