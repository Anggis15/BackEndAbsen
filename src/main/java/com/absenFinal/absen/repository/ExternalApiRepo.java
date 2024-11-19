package com.absenFinal.absen.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 15/11/24 15.19
@Last Modified 15/11/24 15.19
Version 1.0
*/

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "externalService", url = "http://localhost:8080")
public interface ExternalApiRepo {
    @GetMapping("/api/absen/v1/approveAbsen/{id}")
    ResponseEntity<Map<String,Object>> getUserById(@PathVariable("id") Long userId);

    @GetMapping("/v1/absenApprove/{page}/{sort}/{sort-by}")
    ResponseEntity<Map<String,Object>> getAllAbsen(@PathVariable(value = "page") Integer page,//page yang ke ?
                                                   @PathVariable(value = "sort") String sort,//asc desc
                                                   @PathVariable(value = "sort-by") String sortBy);// column Name in java Variable,);
    @PostMapping("api/user/v1/register/supervisior")
    ResponseEntity<Map<String,Object>> regisSupervisior();
    @PostMapping("api/user/v1/register/staff")
    ResponseEntity<Map<String,Object>> regisStaff();
}
