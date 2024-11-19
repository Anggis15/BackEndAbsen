package com.absenFinal.absen.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 15/11/24 15.26
@Last Modified 15/11/24 15.26
Version 1.0
*/

import com.absenFinal.absen.repository.ExternalApiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExternalApiService {

    @Autowired
    private ExternalApiRepo externalApiRepo;

    public ResponseEntity<Map<String,Object>> getAbsenById(Long id){
        return externalApiRepo.getUserById(id);
    }
}
