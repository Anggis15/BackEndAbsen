package com.absenFinal.absen.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 09.06
@Last Modified 11/11/24 09.06
Version 1.0
*/

import com.absenFinal.absen.model.Absen;
import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface AbsenRepo extends JpaRepository<Absen,Long> {
    @Query(value = "SELECT * FROM absen u WHERE DATE(CONVERT_TZ(u.check_in, '+00:00', 'Asia/Jakarta')) = ?1 AND id_staff = ?2", nativeQuery = true)
    Optional<Absen> findByCheckInContainingIgnoreCase(LocalDate checkIn, Long staffId);

    Page<Absen> findBySupervisiorIdAndApprove(Pageable pageable, Supervisior supervisior, Integer Approve);
    Page<Absen> findByStaffId(Pageable pageable, Staff staff);
}
