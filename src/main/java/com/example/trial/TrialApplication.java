package com.example.trial;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrialApplication implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}

	public static void main(String[] args) {
		SpringApplication.run(TrialApplication.class, args);
	}
}
