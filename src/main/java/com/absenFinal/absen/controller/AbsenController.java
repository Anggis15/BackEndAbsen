package com.absenFinal.absen.controller;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 10.51
@Last Modified 11/11/24 10.51
Version 1.0
*/

import com.absenFinal.absen.service.AbsenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/absen")
public class AbsenController {

    @Autowired
    AbsenService absenService;

    private Map<String,Object> mapSorting = new HashMap<>();
    private final String defaultSortingColumnGroupMenu = "id";

    private void mapSorting()
    {
        mapSorting.put("nama","group");
    }

    @GetMapping("/v1/approveAbsen/{id}")
    public ResponseEntity<Object> approveAbsen(@PathVariable("id") Long id, HttpServletRequest request){
        return absenService.approveAbsen(id,request);
    }

    @GetMapping("/v1/absenApprove/{page}/{sort}/{sort-by}")
    public ResponseEntity<Object> showAbsenApprove(
            @PathVariable(value = "page") Integer page,//page yang ke ?
            @PathVariable(value = "sort") String sort,//asc desc
            @PathVariable(value = "sort-by") String sortBy,// column Name in java Variable,
            @RequestParam("size") Integer size,
            HttpServletRequest request){
        page = (page==null)?0:page;
        /** function yang bersifat global di paging , untuk memberikan default jika data request tidak mengirim format sort dengan benar asc/desc */
        sort   = sort.equalsIgnoreCase("desc")?"desc":"asc";
        Object objSortBy = mapSorting.get(sortBy);
        objSortBy = mapSorting.get(sortBy)==null?defaultSortingColumnGroupMenu:mapSorting.get(sortBy);
        Pageable pageable =  PageRequest.of(page,
        (size==null)?10:size,
        sort.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy));
//        Pageable pageable =  PageRequest.of(page,10,
//                Sort.by("checkIn"));

        return absenService.getAbsenseApprove(pageable,request);

    }
    @GetMapping("/v1/getAll")
    public ResponseEntity<Object> getAll(HttpServletRequest request){
//        String token = request.getHeader("Authorization");
        int page = 0;
        Pageable pageable =  PageRequest.of(page,10,
                Sort.by("checkIn"));
        return absenService.getAllAbsen(pageable,request);
    }

    @GetMapping("/v1/checkin")
    public ResponseEntity<Object> doCheckIn(HttpServletRequest request){
//        String token = request.getHeader("Authorization");
        return absenService.absenIn(request);
    }

    @GetMapping("/v1/checkout")
    public ResponseEntity<Object> doCheckout(HttpServletRequest request){
        return absenService.absenOut(request);
    }
}
