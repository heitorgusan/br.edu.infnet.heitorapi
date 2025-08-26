package br.edu.infnet.heitorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HeitorapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeitorapiApplication.class, args);
	}

}
