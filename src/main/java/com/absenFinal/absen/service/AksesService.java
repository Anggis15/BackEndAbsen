package com.absenFinal.absen.service;

import com.absenFinal.absen.core.IService;
import com.absenFinal.absen.dto.response.RespAksesDTO;
import com.absenFinal.absen.dto.validasi.ValAksesDTO;
import com.absenFinal.absen.model.Akses;
import com.absenFinal.absen.model.User;
import com.absenFinal.absen.repository.AksesRepo;
import com.absenFinal.absen.repository.UserRepo;
import com.absenFinal.absen.security.JwtUtility;
import com.absenFinal.absen.util.GlobalFunction;
import com.absenFinal.absen.util.TransformToDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Platform Code : 001
 *  Modul Code : 003
 */
@Service
public class AksesService implements IService<Akses> {

    @Autowired
    private AksesRepo aksesRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransformToDTO transformToDTO;
    private StringBuilder sBuild = new StringBuilder();

    @Autowired
    private JwtUtility jwtUtility;


    @Override
    public ResponseEntity<Object> save(Akses menu, HttpServletRequest request) {
        if(menu==null){
            return GlobalFunction.validasiGagal("OBJECT NULL","FV001003002",request);
        }
        try {
            aksesRepo.save(menu);
        }catch (Exception e){
//            LoggingFile.exceptionStringz("AksesService","save",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE001003002",request);
        }
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    // localhost:8080/api/group-menu/12

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, Akses akses, HttpServletRequest request) {
       Optional<Akses> opAkses =  aksesRepo.findById(id);
       if(opAkses.isEmpty()){
           return GlobalFunction.dataTidakDitemukan(request);
       }
       try {
           Akses aksesDB = opAkses.get();
           aksesDB.setNama(akses.getNama());
           aksesDB.setMenuList(akses.getMenuList());
       }catch (Exception e){
           return GlobalFunction.dataGagalDisimpan("FE001003011",request);
       }

       return GlobalFunction.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<Akses> opAkses =  aksesRepo.findById(id);
        if(!opAkses.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        try {
            aksesRepo.deleteById(id);
        }catch (Exception e){
//            LoggingFile.exceptionStringz("AksesService","delete",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE001003021",request);
        }
        return GlobalFunction.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Akses> page = null;
        List<Akses> list = null;
        try{
            page = aksesRepo.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001003031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespAksesDTO(list), page,null,null,null ,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "" : authorization;
        String token = null;
        String userName = null;
        Map<String, Object> mapzFromToken = null;
        if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
            token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit

        }

        String username = jwtUtility.getUsernameFromToken(token);

        Optional<User> opUser= userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsActive(username,username,username,1);
        if(opUser.get().getAkses()!=null){
            Optional<Akses> opAkses= aksesRepo.findById(opUser.get().getAkses().getId());
            if(!opAkses.isPresent()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
            return GlobalFunction.dataByIdDitemukan(convertToAksesDTO(opAkses.get()),request);
        }else {
            Optional<Akses> opAkses= aksesRepo.findById(2L);
            if(!opAkses.isPresent()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
            return GlobalFunction.dataByIdDitemukan(convertToAksesDTO(opAkses.get()),request);
        }
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable,String columnName, String value, HttpServletRequest request) {
        Page<Akses> page = null;
        List<Akses> list = null;
        try{
            switch (columnName) {
                case "nama": page = aksesRepo.findByNamaContainingIgnoreCase(pageable,value);break;
                default:page = aksesRepo.findAll(pageable);break;
            }
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001003051",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespAksesDTO(list), page,columnName,value,null ,request);
    }

    public RespAksesDTO convertToAksesDTO(Akses groupAkses){
        return modelMapper.map(groupAkses, RespAksesDTO.class);
    }

    public Akses convertToEntity(ValAksesDTO groupAksesDTO){
        return modelMapper.map(groupAksesDTO, Akses.class);
    }

    public List<RespAksesDTO> convertToListRespAksesDTO(List<Akses> groupAksesList){
        return modelMapper.map(groupAksesList,new TypeToken<List<RespAksesDTO>>(){}.getType());
    }
}