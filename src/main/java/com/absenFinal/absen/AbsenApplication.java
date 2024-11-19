package com.absenFinal.absen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AbsenApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbsenApplication.class, args);
	}

}
