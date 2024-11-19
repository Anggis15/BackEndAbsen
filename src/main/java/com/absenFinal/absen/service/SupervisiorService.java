package com.absenFinal.absen.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 20/11/24 01.14
@Last Modified 20/11/24 01.14
Version 1.0
*/

import com.absenFinal.absen.dto.response.ResStaffDTO;
import com.absenFinal.absen.dto.response.RespAksesDTO;
import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import com.absenFinal.absen.repository.SupervisiorRepo;
import com.absenFinal.absen.util.GlobalFunction;
import com.absenFinal.absen.util.TransformToDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SupervisiorService {

    @Autowired
    SupervisiorRepo supervisiorRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TransformToDTO transformToDTO;

    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Supervisior> page = null;
        List<Supervisior> list = null;
        try{
            page = supervisiorRepo.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001003031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespSupervisiorDTO(list), page,null,null,null ,request);
    }
    public List<RespAksesDTO> convertToListRespSupervisiorDTO(List<Supervisior> groupAksesList){
        return modelMapper.map(groupAksesList,new TypeToken<List<ResStaffDTO>>(){}.getType());
    }
}
