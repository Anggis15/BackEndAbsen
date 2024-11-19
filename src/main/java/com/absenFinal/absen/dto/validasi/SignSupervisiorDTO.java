package com.absenFinal.absen.dto.validasi;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 11/11/24 23.53
@Last Modified 11/11/24 23.53
Version 1.0
*/

import jakarta.validation.constraints.NotNull;

public class SignSupervisiorDTO {
    @NotNull
    private Long idStaff;
    @NotNull
    private Long idSupervisior;

    public @NotNull Long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(@NotNull Long idStaff) {
        this.idStaff = idStaff;
    }

    public @NotNull Long getIdSupervisior() {
        return idSupervisior;
    }

    public void setIdSupervisior(@NotNull Long idSupervisior) {
        this.idSupervisior = idSupervisior;
    }
}
