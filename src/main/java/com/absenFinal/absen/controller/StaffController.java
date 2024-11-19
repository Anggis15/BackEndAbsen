package com.absenFinal.absen.controller;

import com.absenFinal.absen.dto.validasi.SignSupervisiorDTO;
import com.absenFinal.absen.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 12/11/24 17.16
@Last Modified 12/11/24 17.16
Version 1.0
*/
@RestController
@RequestMapping("api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    public ResponseEntity<Object> signSupervisior(@Valid @RequestBody SignSupervisiorDTO signSupervisiorDTO, HttpServletRequest request){
        return staffService.signSupervisior(signSupervisiorDTO,request);
    }
}
