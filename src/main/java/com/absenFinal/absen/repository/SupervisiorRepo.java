package com.absenFinal.absen.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 21.47
@Last Modified 11/11/24 21.47
Version 1.0
*/

import com.absenFinal.absen.model.Staff;
import com.absenFinal.absen.model.Supervisior;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupervisiorRepo extends JpaRepository<Supervisior,Long> {
    Optional<Supervisior> findByUserId(Long userId);
}
