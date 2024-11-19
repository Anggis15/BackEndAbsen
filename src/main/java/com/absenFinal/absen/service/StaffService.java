package com.absenFinal.absen.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 23.50
@Last Modified 11/11/24 23.50
Version 1.0
*/

import com.absenFinal.absen.dto.validasi.SignSupervisiorDTO;
import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import com.absenFinal.absen.repository.StaffRepo;
import com.absenFinal.absen.repository.SupervisiorRepo;
import com.absenFinal.absen.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private SupervisiorRepo supervisiorRepo;

    public ResponseEntity<Object> signSupervisior(SignSupervisiorDTO requestSign, HttpServletRequest request){
        Optional<Staff> opStaff = staffRepo.findById(requestSign.getIdStaff());
        Optional<Supervisior> opSupervisior = supervisiorRepo.findById(requestSign.getIdSupervisior());

        if(opStaff.isEmpty()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        Staff staffSign = opStaff.get();
        staffSign.setSupervisiorId(opSupervisior.get());
        staffRepo.save(staffSign);
        return GlobalFunction.dataBerhasilDisimpan(request);
    }
}