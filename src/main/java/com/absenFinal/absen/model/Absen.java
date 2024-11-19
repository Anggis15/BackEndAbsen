package com.absenFinal.absen.model;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 01.09
@Last Modified 11/11/24 01.09
Version 1.0
*/

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "absen")
public class Absen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_staff", foreignKey = @ForeignKey(name = "fk_to_staff"))
    private Staff staffId;
    private String nama;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int approve;
    @ManyToOne
    @JoinColumn(name = "id_supervisior_absen", foreignKey = @ForeignKey(name = "fk_to_supervisior_absen"))
    private Supervisior supervisiorId;
    @Column(length = 4,precision = 2)
    private Double totalWorking;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setStaffId(Staff staffId) {
        this.staffId = staffId;
    }

    public Double getTotalWorking() {
        return totalWorking;
    }

    public void setTotalWorking(Double totalWorking) {
        this.totalWorking = totalWorking;
    }

    public Supervisior getSupervisiorId() {
        return supervisiorId;
    }

    public void setSupervisiorId(Supervisior supervisiorId) {
        this.supervisiorId = supervisiorId;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkId) {
        this.checkIn = checkId;
    }

    public Staff getStaffId() {
        return staffId;
    }

    public void setStaff(Staff staffId) {
        this.staffId = staffId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
