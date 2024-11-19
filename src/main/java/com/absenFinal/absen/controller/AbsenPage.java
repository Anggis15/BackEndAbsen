package com.absenFinal.absen.controller;

import com.absenFinal.absen.dto.response.ResponseAbsenDTO;
import com.absenFinal.absen.repository.ExternalApiRepo;
import com.absenFinal.absen.service.ExternalApiService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 13/11/24 22.21
@Last Modified 13/11/24 22.21
Version 1.0
*/
@Controller
@RequestMapping("page")
public class AbsenPage {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/approve/absen/{id}")
    public String approveAbsen(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ResponseEntity<Map<String,Object>> responseApprove = externalApiService.getAbsenById(id);
        System.out.println(responseApprove);
//        AbsenController absenController
        String url = "http://localhost:8080/api/absen/v1/absenApprove/0/desc/checkIn?size=10";
        RestTemplate restTemplate = new RestTemplate();
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie:cookies){
            if(cookie.getName().equalsIgnoreCase("authLogin")){
                token = cookie.getValue();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);  // Set your token here
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {

            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
            System.out.println(response);
            Map<String,Object> resultData = (Map<String, Object>) response.getBody().get("data");
            List<Map<String,Object>> menuList = (List<Map<String,Object>>) resultData.get("content");
            List<ResponseAbsenDTO> listContent = new ArrayList<>();
            for(Map<String,Object> mapz : menuList){
                ResponseAbsenDTO menu = new ResponseAbsenDTO();
                menu.setId(Long.parseLong(String.valueOf(mapz.get("id"))));
                menu.setNama(String.valueOf(mapz.get("nama")));
                if(mapz.get("checkOut")==null &&mapz.get("totalWorking")==null){
                    menu.setTotalWorking(0.0);
                }else {
                    menu.setCheckOut(LocalDateTime.parse(String.valueOf(mapz.get("checkOut")).replace("T"," "),formatter));
                    menu.setTotalWorking(Double.parseDouble(String.valueOf(mapz.get("totalWorking"))));
                }
                menu.setCheckIn(LocalDateTime.parse(String.valueOf(mapz.get("checkIn")).replace("T"," "),formatter));
                menu.setApprove(Integer.parseInt(String.valueOf(mapz.get("approve"))));
                listContent.add(menu);
            }
            model.addAttribute("listContent", listContent);
            return "menu/listApproveAbsen";

        }catch (Exception e){
            return "menu/listApproveAbsen";
        }
    }

    @GetMapping("/approve")
    public String getListApproveAbsen(Model model, HttpServletRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String url = "http://localhost:8080/api/absen/v1/absenApprove/0/desc/checkIn?size=10";
        RestTemplate restTemplate = new RestTemplate();
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie:cookies){
            if(cookie.getName().equalsIgnoreCase("authLogin")){
                token = cookie.getValue();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);  // Set your token here
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String,Object> resultData = (Map<String, Object>) response.getBody().get("data");
            List<Map<String,Object>> menuList = (List<Map<String,Object>>) resultData.get("content");
            List<ResponseAbsenDTO> listContent = new ArrayList<>();
            for(Map<String,Object> mapz : menuList){
                ResponseAbsenDTO menu = new ResponseAbsenDTO();
                menu.setId(Long.parseLong(String.valueOf(mapz.get("id"))));
                menu.setNama(String.valueOf(mapz.get("nama")));
                if(mapz.get("checkOut")==null &&mapz.get("totalWorking")==null){
                    menu.setTotalWorking(0.0);
                }else {
                    menu.setCheckOut(LocalDateTime.parse(String.valueOf(mapz.get("checkOut")).replace("T"," "),formatter));
                    menu.setTotalWorking(Double.parseDouble(String.valueOf(mapz.get("totalWorking"))));
                }
                menu.setCheckIn(LocalDateTime.parse(String.valueOf(mapz.get("checkIn")).replace("T"," "),formatter));
                menu.setApprove(Integer.parseInt(String.valueOf(mapz.get("approve"))));
                listContent.add(menu);
            }
            model.addAttribute("listContent", listContent);
            return "menu/listApproveAbsen";

        }catch (Exception e){
            return "menu/listApproveAbsen";
        }
    }

    @GetMapping("/absen")
    public String getAbsenPage(){
        return "menu/absen";
    }
    @GetMapping("/absen/in")
    public String getAbsenIn(HttpServletRequest request, Model model){
        String url = "http://localhost:8080/api/absen/v1/checkin";
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie:cookies){
            if(cookie.getName().equalsIgnoreCase("authLogin")){
                token = cookie.getValue();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);  // Set your token here
        headers.set("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            String ada = "adaIn";
            model.addAttribute("response", ada);
            return "menu/absen";
        }catch (Exception e){
            String ada = "gaAdaIn";
            model.addAttribute("response", ada);
            return "menu/absen";
        }

    }
    @GetMapping("/absen/out")
    public String getAbsenout(HttpServletRequest request,Model model){
        String url = "http://localhost:8080/api/absen/v1/checkout";
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie:cookies){
            if(cookie.getName().equalsIgnoreCase("authLogin")){
                token = cookie.getValue();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);  // Set your token here
        headers.set("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            String ada = "adaOut";
            model.addAttribute("response", ada);
            return "menu/absen";
        }catch (Exception e){
            String ada = "gaAdaOut";
            model.addAttribute("response", ada);
            return "menu/absen";
        }
    }

    @GetMapping("/list-absen")
    public String getListAbsen(Model model, HttpServletRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String url = "http://localhost:8080/api/absen/v1/getAll";
        RestTemplate restTemplate = new RestTemplate();
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie:cookies){
            if(cookie.getName().equalsIgnoreCase("authLogin")){
                token = cookie.getValue();
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);  // Set your token here
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
                Map<String,Object> resultData = (Map<String, Object>) response.getBody().get("data");
                List<Map<String,Object>> menuList = (List<Map<String,Object>>) resultData.get("content");
                List<ResponseAbsenDTO> listContent = new ArrayList<>();
                for(Map<String,Object> mapz : menuList){
                    ResponseAbsenDTO menu = new ResponseAbsenDTO();

                    menu.setId(Long.parseLong(String.valueOf(mapz.get("id"))));
                    menu.setNama(String.valueOf(mapz.get("nama")));
                    menu.setCheckIn(LocalDateTime.parse(String.valueOf(mapz.get("checkIn")).replace("T"," "),formatter));
                    if(mapz.get("checkOut")==null &&mapz.get("totalWorking")==null){
                        menu.setTotalWorking(0.0);
                    }else {
                        menu.setCheckOut(LocalDateTime.parse(String.valueOf(mapz.get("checkOut")).replace("T"," "),formatter));
                        menu.setTotalWorking(Double.parseDouble(String.valueOf(mapz.get("totalWorking"))));
                    }
                    menu.setApprove(Integer.parseInt(String.valueOf(mapz.get("approve"))));
                    listContent.add(menu);
                }
                model.addAttribute("listContent", listContent);
                return "menu/listAbsen";

        }catch (Exception e){
            return "menu/listAbsen";
        }
    }
}
