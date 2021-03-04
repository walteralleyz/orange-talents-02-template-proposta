package br.com.zup.Credicard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertTrue;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class CredicardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CredicardApplication.class, args);
	}

}
