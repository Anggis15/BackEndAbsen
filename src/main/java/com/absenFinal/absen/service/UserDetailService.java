package com.absenFinal.absen.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/10/24 22.40
@Last Modified 16/10/24 22.40
Version 1.0
*/

import com.absenFinal.absen.dto.validasi.LoginDTO;
import com.absenFinal.absen.dto.validasi.RegisDTO;
import com.absenFinal.absen.dto.validasi.RegisEmployeeDTO;
import com.absenFinal.absen.model.Akses;
import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import com.absenFinal.absen.model.User;
import com.absenFinal.absen.repository.StaffRepo;
import com.absenFinal.absen.repository.SupervisiorRepo;
import com.absenFinal.absen.repository.UserRepo;
import com.absenFinal.absen.security.BcryptImpl;
import com.absenFinal.absen.security.JwtUtility;
import com.absenFinal.absen.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    StaffRepo employeeRepo;

    @Autowired
    SupervisiorRepo supervisiorRepo;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    private ModelMapper modelMapper;


    private Map<String,Object> m = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<com.absenFinal.absen.model.User> opUser = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsActive(s,s,s,1);
        if(opUser.isEmpty())
        {
            throw new UsernameNotFoundException("TOKEN TIDAK VALID");
//            return null;
        }
        com.absenFinal.absen.model.User userNext = opUser.get();

        return new org.springframework.security.core.userdetails.
                User(userNext.getUsername(),userNext.getPassword(),userNext.getAuthorities());
    }

    public ResponseEntity<Object> doLogin(User user, HttpServletRequest request) throws UsernameNotFoundException {

        String s = user.getUsername();
        Optional<User> opUserResult = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsActive(s,s,s,1);//DATANYA PASTI HANYA 1
        User nextUser = null;
        if (!opUserResult.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        nextUser = opUserResult.get();
        if(!BcryptImpl.verifyHash(user.getPassword()+nextUser.getUsername(),
                nextUser.getPassword()))//dicombo dengan userName
        {
            return GlobalFunction.customReponse("X-03-031","username dan password salah",request);
        }

        Map<String,Object> mapForClaims = new HashMap<>();
        if(nextUser.getAkses().getId()==1L){
            Optional<Staff> opStaff = employeeRepo.findByUserId(opUserResult.get().getId());
            mapForClaims.put("employeeId", opStaff.get().getId());
        }
        if(nextUser.getAkses().getId()==3L){
            Optional<Supervisior> opSupervisior = supervisiorRepo.findByUserId(opUserResult.get().getId());
            mapForClaims.put("employeeId", opSupervisior.get().getId());
        }

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        /** start jwt config */

        mapForClaims.put("uid",nextUser.getId());//payload
        mapForClaims.put("email",nextUser.getEmail());//payload
        mapForClaims.put("password",nextUser.getPassword());//payload
        String token = jwtUtility.generateToken(userDetails,mapForClaims);
        m.put("token", token);
        return  GlobalFunction.customDataDitemukan("Login Berhasil",m,request);
    }

    public ResponseEntity<Object> doRegis(User user, HttpServletRequest request) {

        Optional<User> userRegisted = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsActive(user.getUsername(),user.getNoHp(),user.getEmail(),1);//DATANYA PASTI HANYA 1
        //sebenar nya disini flow nya ada banyak
        //pertama cek terlebih dahulu apakah user pernah melakukan verifikasi atau tidak
        //kedua jika sudah pernah , maka cek keseluruhan data nya terlebih dahulu dimana data yg sudah terpakai maka diinformasikan ke client
        //biarkan saja error jika saat pengecekan ternyata sudah terdaftar akan tetapi pada saat dicoba dimasukkan ke database tidak bisa karena unique per kolom di no hp , email dan usernam
        if(userRegisted.isEmpty()){
            user.setPassword(BcryptImpl.hash(user.getPassword()+user.getUsername()));//password harus dikombinasikan
            Akses akses = new Akses();
            akses.setId(2L);
            user.setAkses(akses);
            user.setIsActive(1);
            userRepo.save(user);
        }else{
            return GlobalFunction.customReponse("X-03-041","Registrasi Gagal / Beberapa Informasi sudah Digunakan, Coba ubah data no hp , email dan username dengan data lain nya",request);
        }
        return  GlobalFunction.customDataDitemukan("Berhasil melakukan registrasi",m,request);
    }

    public ResponseEntity<Object> doRegisEmployeeStaff(User user, HttpServletRequest request){
        Staff employee = new Staff();
        Optional<User> userRegisted = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsActive(user.getUsername(),user.getNoHp(),user.getEmail(),1);//DATANYA PASTI HANYA 1
        if(userRegisted.isEmpty()){
            user.setPassword(BcryptImpl.hash(user.getPassword()+user.getUsername()));//password harus dikombinasikan
            Akses akses = new Akses();
            akses.setId(1L);
            user.setAkses(akses);
            User userSave = userRepo.save(user);
            employee.setUserId(userSave.getId());
            employee.setNama(userSave.getNamaLengkap());
            employee.setIsActive(1);
            employeeRepo.save(employee);

        }else{
            return GlobalFunction.customReponse("X-03-041","Registrasi Gagal / Beberapa Informasi sudah Digunakan, Coba ubah data no hp , email dan username dengan data lain nya",request);
        }
        return  GlobalFunction.customDataDitemukan("Berhasil melakukan registrasi",m,request);


    }

    public ResponseEntity<Object> doRegisEmployeeSupervisior(User user, HttpServletRequest request){
        Supervisior employee = new Supervisior();
        Optional<User> userRegisted = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsActive(user.getUsername(),user.getNoHp(),user.getEmail(),1);//DATANYA PASTI HANYA 1
        if(userRegisted.isEmpty()){
            user.setPassword(BcryptImpl.hash(user.getPassword()+user.getUsername()));//password harus dikombinasikan
            Akses akses = new Akses();
            akses.setId(3L);
            user.setAkses(akses);
            User userSave = userRepo.save(user);
            employee.setUserId(userSave.getId());
            employee.setNama(userSave.getNamaLengkap());
            employee.setIsActive(1);
            supervisiorRepo.save(employee);
        }else{
            return GlobalFunction.customReponse("X-03-041","Registrasi Gagal / Beberapa Informasi sudah Digunakan, Coba ubah data no hp , email dan username dengan data lain nya",request);
        }
        return  GlobalFunction.customDataDitemukan("Berhasil melakukan registrasi",m,request);


    }
    public User convertToEntity(RegisDTO regisDTO){
        return modelMapper.map(regisDTO,User.class);
    }
    public User convertToEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }
    public User convertToEntity(RegisEmployeeDTO regisDTO){
        return modelMapper.map(regisDTO,User.class);
    }
}
