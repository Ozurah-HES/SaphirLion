package ch.hearc.SaphirLion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SaphirLionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaphirLionApplication.class, args);
	}
}
