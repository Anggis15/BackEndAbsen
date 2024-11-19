package com.absenFinal.absen.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/10/24 22.37
@Last Modified 16/10/24 22.37
Version 1.0
*/

import com.absenFinal.absen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findTop1ByUsernameOrNoHpOrEmailAndIsActive(String usr, String noHp, String mail,Integer isActive);
}
