package com.absenFinal.absen.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 09.07
@Last Modified 11/11/24 09.07
Version 1.0
*/

import com.absenFinal.absen.dto.response.RespAbsenListApproveDTO;
import com.absenFinal.absen.dto.response.ResponseAbsenDTO;
import com.absenFinal.absen.model.Absen;
import com.absenFinal.absen.model.Akses;
import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import com.absenFinal.absen.repository.AbsenRepo;
import com.absenFinal.absen.repository.StaffRepo;
import com.absenFinal.absen.repository.SupervisiorRepo;
import com.absenFinal.absen.repository.UserRepo;
import com.absenFinal.absen.security.JwtUtility;
import com.absenFinal.absen.util.GlobalFunction;
import com.absenFinal.absen.util.ParseToken;
import com.absenFinal.absen.util.TransformToDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.NonUniqueResultException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AbsenService {

    @Autowired
    private AbsenRepo absenRepo;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StaffRepo employeeRepo;

    @Autowired
    private SupervisiorRepo supervisiorRepo;

    @Autowired
    private TransformToDTO transformToDTO;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> approveAbsen(Long id, HttpServletRequest request){

        Optional<Absen> opAbsen = absenRepo.findById(id);
        if(opAbsen.isEmpty()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        Absen absenApprove = opAbsen.get();

        absenApprove.setApprove(1);

        absenRepo.save(absenApprove);
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    public ResponseEntity<Object> getAbsenseApprove(Pageable pageable, HttpServletRequest request){
        Page<Absen> page = null;
        List<Absen> list = null;
        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "" : authorization;
        String token = null;
        String userName = null;
        Map<String, Object> mapzFromToken = null;
        if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
            token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
//                token = Crypto.performDecrypt(token);
            mapzFromToken = jwtUtility.mappingBodyToken(token, "Absen");

        }

        Long supervisiorId = Long.parseLong(String.valueOf(mapzFromToken.get("employeeId")));
        Optional<Supervisior> opSupervisior = supervisiorRepo.findById(supervisiorId);
        if (opSupervisior.isPresent()){
            page = absenRepo.findBySupervisiorIdAndApprove(pageable, opSupervisior.get(),0);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
            return transformToDTO.
                    transformObject(new HashMap<>(),
                            convertToListRespAbsenApproveDTO(list), page,"checkIn",null,null ,request);
        }
        return GlobalFunction.tidakDapatDiproses("FE002002051",request);

    }


    public ResponseEntity<Object> absenIn(HttpServletRequest request) {
        LocalDate dateToday =LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime jakartaNow = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        LocalDateTime date = LocalDateTime.parse(jakartaNow.toLocalDateTime().format(formatter),formatter);


        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "" : authorization;
        String token = null;
        String userName = null;
        Map<String, Object> mapzFromToken = null;
        if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
            token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
//                token = Crypto.performDecrypt(token);
            mapzFromToken = jwtUtility.mappingBodyToken(token, "Absen");

        }

        Long staffId = Long.parseLong(String.valueOf(mapzFromToken.get("employeeId")));


        Optional<Staff> optionalEmployee = employeeRepo.findById(staffId);
        Optional<Absen> absenOp = absenRepo.findByCheckInContainingIgnoreCase(dateToday, optionalEmployee.get().getId());


        Absen absenIn = new Absen();
            if(absenOp.isEmpty()){
                absenIn.setCheckIn(date);
                if (optionalEmployee.isPresent()) {
                    absenIn.setStaff((optionalEmployee.get()));
                    absenIn.setNama(optionalEmployee.get().getNama());
                    absenIn.setSupervisiorId(optionalEmployee.get().getSupervisiorId());
                } else {
                    return GlobalFunction.dataTidakDitemukan(request);
                }

                absenRepo.save(absenIn);
                return GlobalFunction.dataBerhasilDisimpan(request);
            }else {
                return GlobalFunction.tidakDapatDiproses("F010101", request);
            }

    }

    public ResponseEntity<Object> absenOut(HttpServletRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime jakartaNow = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        LocalDateTime date = LocalDateTime.parse(jakartaNow.toLocalDateTime().format(formatter),formatter);
        ParseToken parseToken = new ParseToken();
        LocalDate dateToday =LocalDate.now();

        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "" : authorization;
        String token = null;
        String userName = null;
        Map<String, Object> mapzFromToken = null;
        if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
            token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
//                token = Crypto.performDecrypt(token);
            mapzFromToken = jwtUtility.mappingBodyToken(token, "Absen");

        }

        Long staffId = Long.parseLong(String.valueOf(mapzFromToken.get("employeeId")));

        Optional<Staff> opStaff = employeeRepo.findById(staffId);

        try{
            Optional<Absen> opAbsen = absenRepo.findByCheckInContainingIgnoreCase(dateToday,opStaff.get().getId());
            if(opAbsen.isPresent()&&opAbsen.get().getCheckOut()==null){
                Absen absenIn = opAbsen.get();
                absenIn.setCheckOut(date);

                absenIn.setTotalWorking(ParseToken.calculateWorkingHours(absenIn.getCheckIn(),absenIn.getCheckOut()));

                absenRepo.save(absenIn);

                return GlobalFunction.dataBerhasilDisimpan(request);
            }else {
                return GlobalFunction.dataGagalDisimpan("FE010101", request);
            }

        }catch (NonUniqueResultException e){
            return GlobalFunction.tidakDapatDiproses("FE020202", request);
        }
    }

    public ResponseEntity<Object> getAllAbsen(Pageable pageable, HttpServletRequest request){
        Page<Absen> page = null;
        List<Absen> list = null;
        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "" : authorization;
        String token = null;
        String userName = null;
        Map<String, Object> mapzFromToken = null;
        if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
            token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
//                token = Crypto.performDecrypt(token);
            mapzFromToken = jwtUtility.mappingBodyToken(token, "Absen");

        }
        Long staffId = null;
        if(mapzFromToken.get("employeeId")==null){
            page = absenRepo.findAll(pageable);
        }else {
            staffId = Long.parseLong(String.valueOf(mapzFromToken.get("employeeId")));
            Optional<Staff> opStaff = employeeRepo.findById(staffId);
            if(opStaff.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
            page = absenRepo.findByStaffId(pageable, opStaff.get());
        }

        try{
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001003031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespAbsenDTO(list), page,null,null,null ,request);
    }
    public List<ResponseAbsenDTO> convertToListRespAbsenDTO(List<Absen> groupUserList){
        return modelMapper.map(groupUserList,new TypeToken<List<ResponseAbsenDTO>>(){}.getType());
    }
    public List<RespAbsenListApproveDTO> convertToListRespAbsenApproveDTO(List<Absen> groupUserList){
        return modelMapper.map(groupUserList,new TypeToken<List<RespAbsenListApproveDTO>>(){}.getType());
    }

}
