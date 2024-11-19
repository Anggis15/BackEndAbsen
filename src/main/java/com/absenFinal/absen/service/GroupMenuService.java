package com.absenFinal.absen.service;

import com.absenFinal.absen.core.IService;
import com.absenFinal.absen.dto.response.RespGroupMenuDTO;
import com.absenFinal.absen.dto.validasi.ValGroupMenuDTO;
import com.absenFinal.absen.model.GroupMenu;
import com.absenFinal.absen.repository.GroupMenuRepo;
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
 *  Modul Code : 001
 *  FV -> Failed Validation
 *  FE -> Failed Error
 */
@Service
public class GroupMenuService implements IService<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformToDTO transformToDTO;
    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request) {
        if(groupMenu==null){
            return GlobalFunction.validasiGagal("OBJECT NULL","FV001001001",request);
        }
        try {
            groupMenuRepo.save(groupMenu);
        }catch (Exception e){
//            LoggingFile.exceptionStringz("GroupMenuService","save",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE001001001",request);
        }
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    // localhost:8080/api/group-menu/12

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, GroupMenu groupMenu, HttpServletRequest request) {
       Optional<GroupMenu> opGroupMenu =  groupMenuRepo.findById(id);
       if(!opGroupMenu.isPresent()){
           return GlobalFunction.dataTidakDitemukan(request);
       }
       try {
           GroupMenu groupMenuDB = opGroupMenu.get();
           groupMenuDB.setName(groupMenu.getName());
           groupMenuDB.setUpdatedBy(1L);
       }catch (Exception e){
//           LoggingFile.exceptionStringz("GroupMenuService","update",e,OtherConfig.getFlagLogging());
           return GlobalFunction.dataGagalDisimpan("FE001001011",request);
       }

       return GlobalFunction.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<GroupMenu> opGroupMenu =  groupMenuRepo.findById(id);
        if(!opGroupMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        try {
            groupMenuRepo.deleteById(id);
        }catch (Exception e){
//            LoggingFile.exceptionStringz("GroupMenuService","delete",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE001001021",request);
        }
        return GlobalFunction.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        try{
            page = groupMenuRepo.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001001031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespGroupMenuDTO(list), page,null,null,null ,request);
//        return new ResponseHandler().
//                generateResponse("PERMINTAAN DATA BERHASIL",
//                        HttpStatus.OK,
//                        page,
//                        null,
//                        request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<GroupMenu> opGroupMenu=groupMenuRepo.findById(id);
        if(!opGroupMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        return GlobalFunction.dataByIdDitemukan(convertToGroupMenuDTO(opGroupMenu.get()),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable,String columnName, String value, HttpServletRequest request) {
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        try{
            switch (columnName) {
                case "name": page = groupMenuRepo.findByNameContainsIgnoreCase(pageable,value);break;
                default:page = groupMenuRepo.findAll(pageable);break;
            }
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001001051",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespGroupMenuDTO(list), page,columnName,value,null ,request);
    }

    public RespGroupMenuDTO convertToGroupMenuDTO(GroupMenu groupMenu){
        return modelMapper.map(groupMenu, RespGroupMenuDTO.class);
    }

    public GroupMenu convertToEntity(ValGroupMenuDTO groupMenuDTO){
        return modelMapper.map(groupMenuDTO, GroupMenu.class);
    }

    public List<RespGroupMenuDTO> convertToListRespGroupMenuDTO(List<GroupMenu> groupMenuList){
        return modelMapper.map(groupMenuList,new TypeToken<List<RespGroupMenuDTO>>(){}.getType());
    }
}