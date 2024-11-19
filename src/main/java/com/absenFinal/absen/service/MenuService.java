package com.absenFinal.absen.service;

import com.absenFinal.absen.core.IService;
import com.absenFinal.absen.dto.response.RespMenuDTO;
import com.absenFinal.absen.dto.validasi.ValMenuDTO;
import com.absenFinal.absen.model.Menu;
import com.absenFinal.absen.repository.MenuRepo;
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

import java.util.*;


/**
 *  Platform Code : 002
 *  Modul Code : 002
 *  FV -> Failed Validation
 *  FE -> Failed Error
 */
@Service
public class MenuService implements IService<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformToDTO transformToDTO;
    private StringBuilder sBuild = new StringBuilder();


    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {

        if(menu==null){
            return GlobalFunction.validasiGagal("OBJECT NULL","FV002002002",request);
        }

        try {
            menuRepo.save(menu);
        }catch (Exception e){
//            LoggingFile.exceptionStringz("MenuService","save",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE002002002",request);
        }

        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    // localhost:8080/api/group-menu/12

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
       Optional<Menu> opMenu =  menuRepo.findById(id);
       if(!opMenu.isPresent()){
           return GlobalFunction.dataTidakDitemukan(request);
       }
       try {
           Menu menuDB = opMenu.get();
           menuDB.setNama(menu.getNama());
       }catch (Exception e){
//           LoggingFile.exceptionStringz("MenuService","update",e,OtherConfig.getFlagLogging());
           return GlobalFunction.dataGagalDisimpan("FE002002011",request);
       }

       return GlobalFunction.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<Menu> opMenu =  menuRepo.findById(id);
        if(!opMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        try {
            menuRepo.deleteById(id);
        }catch (Exception e){
//            LoggingFile.exceptionStringz("MenuService","delete",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE002002021",request);
        }
        return GlobalFunction.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        try{
            page = menuRepo.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE002002031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespMenuDTO(list), page,null,null,null ,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<Menu> opMenu= menuRepo.findById(id);
        if(!opMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        return GlobalFunction.dataByIdDitemukan(convertToMenuDTO(opMenu.get()),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable,String columnName, String value, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        try{
            switch (columnName) {
                case "nama": page = menuRepo.findByNamaContainsIgnoreCase(pageable,value);break;
                default:page = menuRepo.findAll(pageable);break;
            }
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE002002051",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespMenuDTO(list), page,columnName,value,null ,request);
    }

    public RespMenuDTO convertToMenuDTO(Menu groupMenu){
        return modelMapper.map(groupMenu, RespMenuDTO.class);
    }

    public Menu convertToEntity(ValMenuDTO groupMenuDTO){
        return modelMapper.map(groupMenuDTO, Menu.class);
    }

    public List<RespMenuDTO> convertToListRespMenuDTO(List<Menu> groupMenuList){
        return modelMapper.map(groupMenuList,new TypeToken<List<RespMenuDTO>>(){}.getType());
    }
}