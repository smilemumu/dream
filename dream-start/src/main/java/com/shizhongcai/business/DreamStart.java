package com.shizhongcai.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class DreamStart {

	public static void main(String[] args) {
		SpringApplication.run(DreamStart.class, args);
	}

}
