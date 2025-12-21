package be.odisee.jzzz;

import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "be.odisee.jzzz.dao")
@EntityScan(basePackages = "be.odisee.jzzz.domain")
public class JzzzLedenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JzzzLedenApplication.class, args);
	}

		

}
