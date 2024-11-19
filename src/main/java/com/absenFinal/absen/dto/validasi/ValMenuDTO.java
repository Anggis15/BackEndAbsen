package com.absenFinal.absen.dto.validasi;

import com.absenFinal.absen.dto.response.RespAksesDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

public class ValMenuDTO {

    private Long id;

    private String nama;

    private String path;

    private ValGroupMenuDTO groupMenu;

    @JsonBackReference
    private List<RespAksesDTO> menuAkses ;

    public List<RespAksesDTO> getMenuAkses() {
        return menuAkses;
    }

    public void setMenuAkses(List<RespAksesDTO> menuAkses) {
        this.menuAkses = menuAkses;
    }

    public ValGroupMenuDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(ValGroupMenuDTO groupMenu) {
        this.groupMenu = groupMenu;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
