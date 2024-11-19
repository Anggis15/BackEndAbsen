package com.absenFinal.absen.dto.response;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 12/11/24 16.27
@Last Modified 12/11/24 16.27
Version 1.0
*/

import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ResponseAbsenDTO {
    private Long id;
    private String nama;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int approve;
    private Double totalWorking;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }


    public Double getTotalWorking() {
        return totalWorking;
    }

    public void setTotalWorking(Double totalWorking) {
        this.totalWorking = totalWorking;
    }
}
