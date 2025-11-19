package be.odisee.brainstorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@SpringBootApplication
@EntityScan("be.odisee.brainstorm.domain")
public class Brainstorm2018Wa2sbApplication {

	public static void main(String[] args) {
		SpringApplication.run(Brainstorm2018Wa2sbApplication.class, args);
	}
	
}
