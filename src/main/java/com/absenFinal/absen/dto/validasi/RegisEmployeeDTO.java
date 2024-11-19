package com.absenFinal.absen.dto.validasi;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 10/11/24 18.41
@Last Modified 10/11/24 18.41
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RegisEmployeeDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String alamat;

    @NotNull
    @NotBlank(message = "NAMA TIDAK BOLEH NULL !!")
    @NotEmpty
    @JsonProperty("nama_lengkap")
    private String namaLengkap;

    @NotNull
    @NotBlank
    @NotEmpty
    @JsonProperty("no_hp")
    private String noHp;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @NotNull
    @NotBlank
    @NotEmpty
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate tanggalLahir;

    public @NotNull @NotBlank @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotBlank @NotEmpty String email) {
        this.email = email;
    }

    public @NotNull @NotBlank @NotEmpty String getAlamat() {
        return alamat;
    }

    public void setAlamat(@NotNull @NotBlank @NotEmpty String alamat) {
        this.alamat = alamat;
    }

    public @NotNull @NotBlank(message = "NAMA TIDAK BOLEH NULL !!") @NotEmpty String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(@NotNull @NotBlank(message = "NAMA TIDAK BOLEH NULL !!") @NotEmpty String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public @NotNull @NotBlank @NotEmpty String getNoHp() {
        return noHp;
    }

    public void setNoHp(@NotNull @NotBlank @NotEmpty String noHp) {
        this.noHp = noHp;
    }

    public @NotNull @NotBlank @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotBlank @NotEmpty String password) {
        this.password = password;
    }

    public @NotNull @NotBlank @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank @NotEmpty String username) {
        this.username = username;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
