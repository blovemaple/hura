package com.github.blovemaple.hura;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.github.blovemaple.hura")
public class HuraApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuraApplication.class, args);
	}
}
