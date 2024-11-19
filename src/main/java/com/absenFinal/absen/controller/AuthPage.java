package com.absenFinal.absen.controller;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 12/11/24 23.23
@Last Modified 12/11/24 23.23
Version 1.0
*/

import com.absenFinal.absen.dto.response.RespMenuDTO;
import com.absenFinal.absen.dto.validasi.LoginDTO;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("page")
public class AuthPage {

    @Autowired
    AuthController authController;

    @GetMapping("/login")
    public String loginPage(Model model){
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("loginModel", loginDTO);
        return "auth/login";
    }

    @GetMapping("/login/menu")
    public String getMenuUtama(HttpServletRequest request, Model model){
        try {
            String apiUrlAkses = "http://localhost:8080/api/akses/v1/listAkses";
            Cookie[] cookies = request.getCookies();
            String token = null;
            for (Cookie cookie:cookies){
                if(cookie.getName().equalsIgnoreCase("authLogin")){
                    token = cookie.getValue();
                }
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+token);  // Set your token here
            headers.set("Content-Type", "application/json");  // Set Content-Type header

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map<String,Object>> responseAkses = restTemplate.exchange(
                    apiUrlAkses,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String,Object> resultData = (Map<String, Object>) responseAkses.getBody().get("data");
            Object resultGroupMenu = resultData.get("menuList");
            List<Map<String,Object>> menuList = (List<Map<String,Object>>) resultData.get("menuList");
            List<RespMenuDTO> listMenuList = new ArrayList<>();
            for(Map<String,Object> mapz : menuList){
                RespMenuDTO menu = new RespMenuDTO();
                menu.setId(Long.parseLong(String.valueOf(mapz.get("id"))));
                menu.setNama(String.valueOf(mapz.get("nama")));
                menu.setPath(String.valueOf(mapz.get("path")));
                listMenuList.add(menu);
            }
            model.addAttribute("menuList", listMenuList);

            return "menu/menuutama";
        }catch (Exception e){
            return "jamkerja";
        }
    }

    @PostMapping("/login/menu")
    public String submitLogin(HttpServletRequest request, Model model, @ModelAttribute("loginModel") LoginDTO loginDTO, HttpServletResponse httpResponse){
        RestTemplate restTemplate = new RestTemplate();
        String apiUrlLogin = "http://localhost:8080/api/auth/v1/login";  // Replace with your actual API URL
        String apiUrlAkses = "http://localhost:8080/api/akses/v1/listAkses";
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrlLogin, loginDTO, String.class);
        if(response.getBody().matches("\"success\":true")){
            return "jamkerja";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(response.getBody(), Map.class);
            Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
            String token =(String) dataMap.get("token");
            Cookie cookie = new Cookie("authLogin", token);
            cookie.setMaxAge(3600); // Cookie will expire in 1 hour
            cookie.setPath("/");    // Cookie will be valid for the entire domain
//            cookie.setSecure(true); // Only send cookie over HTTPS
            cookie.setHttpOnly(true); // Cookie won't be accessible via JavaScript
            httpResponse.addCookie(cookie);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+token);  // Set your token here
            headers.set("Content-Type", "application/json");  // Set Content-Type header

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map<String,Object>> responseAkses = restTemplate.exchange(
                    apiUrlAkses,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String,Object> resultData = (Map<String, Object>) responseAkses.getBody().get("data");
            Object resultGroupMenu = resultData.get("menuList");
            List<Map<String,Object>> menuList = (List<Map<String,Object>>) resultData.get("menuList");
            List<RespMenuDTO> listMenuList = new ArrayList<>();
            for(Map<String,Object> mapz : menuList){
                RespMenuDTO menu = new RespMenuDTO();
                menu.setId(Long.parseLong(String.valueOf(mapz.get("id"))));
                menu.setNama(String.valueOf(mapz.get("nama")));
                menu.setPath(String.valueOf(mapz.get("path")));
                listMenuList.add(menu);
            }
            model.addAttribute("menuList", listMenuList);

            return "menu/menuutama";
        }catch (Exception e){
            return "jamkerja";
        }

    }
}
