package com.absenFinal.absen.dto.response;

import com.absenFinal.absen.dto.validasi.ValMenuDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

public class RespAksesDTO {

    private Long id;
    private String nama;

    private List<ValMenuDTO> menuList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<ValMenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<ValMenuDTO> menuList) {
        this.menuList = menuList;
    }
}
