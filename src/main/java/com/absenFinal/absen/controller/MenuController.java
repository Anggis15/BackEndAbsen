package com.absenFinal.absen.controller;


import com.absenFinal.absen.dto.validasi.ValMenuDTO;
import com.absenFinal.absen.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    private Map<String,Object> mapSorting = new HashMap<>();
    private final String defaultSortingColumnGroupMenu = "id";

    private void mapSorting()
    {
        mapSorting.put("nama","group");
    }

    /** saat user melakukan mengklik menu group menu , ini adalah api default nya /api/menu*/
    @GetMapping
//    @PreAuthorize("hasAuthority('MENU')")
    public ResponseEntity<Object> defaultPage(
            HttpServletRequest request
    ){
        Pageable pageable =  PageRequest.of(0,10,
                Sort.by("id"));
        return menuService.findAll(pageable,request);
    }

//    @PreAuthorize("hasAuthority('MENU')")
    @PostMapping("/v1")
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO valMenuDTO,
                                       HttpServletRequest request
    ){
        return menuService.save(menuService.convertToEntity(valMenuDTO), request);
    }

//    @PreAuthorize("hasAuthority('MENU')")
    @PutMapping("/v1/{id}")
    public ResponseEntity<Object> update(
                                        @PathVariable Long id,
                                        @Valid @RequestBody ValMenuDTO valMenuDTO,
                                        HttpServletRequest request
    ){
        return menuService.update(id, menuService.convertToEntity(valMenuDTO), request);
    }

//    @PreAuthorize("hasAuthority('MENU')")
    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        return menuService.delete(id, request);
    }

//    @PreAuthorize("hasAuthority('MENU')")
    @GetMapping("/v1/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,HttpServletRequest request){
        return menuService.findById(id,request);
    }


//    localhost:8080/api/v1/all/0/asc/id?column_name=id&value=no&size=2
    @GetMapping("/v1/all/{page}/{sort}/{sort-by}")
    public ResponseEntity<Object> findAll(
            @PathVariable(value = "page") Integer page,//page yang ke ?
            @PathVariable(value = "sort") String sort,//asc desc
            @PathVariable(value = "sort-by") String sortBy,// column Name in java Variable,
            @RequestParam("size") Integer size,
            HttpServletRequest request
    ){
        page = (page==null)?0:page;
        /** function yang bersifat global di paging , untuk memberikan default jika data request tidak mengirim format sort dengan benar asc/desc */
        sort   = sort.equalsIgnoreCase("desc")?"desc":"asc";
        Object objSortBy = mapSorting.get(sortBy);
        objSortBy = mapSorting.get(sortBy)==null?defaultSortingColumnGroupMenu:mapSorting.get(sortBy);
//        Pageable pageable =  PageRequest.of(page,
//        (size==null)?10:size,
//        sort.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy)
        Pageable pageable =  PageRequest.of(page,size,
                Sort.by("id"));
        return menuService.findAll(pageable,request);
    }
}