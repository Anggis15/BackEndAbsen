package com.absenFinal.absen.controller;

import com.absenFinal.absen.dto.validasi.RegisEmployeeDTO;
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
Created on 10/11/24 18.37
@Last Modified 10/11/24 18.37
Version 1.0
*/
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserDetailService userDetailService;

    @PostMapping("/v1/register/staff")
    public ResponseEntity<Object> doRegisterStaff(@Valid @RequestBody RegisEmployeeDTO employeeDTO, HttpServletRequest request){
        User user = userDetailService.convertToEntity(employeeDTO);
        return userDetailService.doRegisEmployeeStaff(user,request);
    }

    @PostMapping("/v1/register/supervisior")
    public ResponseEntity<Object> doRegisterSupervisior(@Valid @RequestBody RegisEmployeeDTO employeeDTO, HttpServletRequest request){
        User user = userDetailService.convertToEntity(employeeDTO);
        return userDetailService.doRegisEmployeeSupervisior(user,request);
    }

}
