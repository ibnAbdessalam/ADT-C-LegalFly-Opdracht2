package be.odisee.jzzz;

import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JzzzLedenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JzzzLedenApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(LidRepository repo) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.MONTH, -1);  
		Date oneMonthAgo = cal.getTime();
		cal.add(Calendar.YEAR, +1);
		Date elevenMonthsFromNow = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date tenMonthsFromNow = cal.getTime();
		return (evt) -> {
			repo.save(new Lid("Miles", "Davies", "miles@davies.com", today));
			repo.save(new Lid("Ella", "Fitzgerald", "ella@fitzgerald.com", oneMonthAgo));
			repo.save(new Lid("Diana", "Krall", "diana@krall.com", oneMonthAgo));
			repo.save(new Lid("Keith", "Jarrett", "keith@jarrett.com", elevenMonthsFromNow));
			repo.save(new Lid("Keith", "Richards", "keith@richards.com", today));
			repo.save(new Lid("Sarah", "Vaughan", "sarah@vaughan.com", elevenMonthsFromNow));
			repo.save(new Lid("Chris", "Joris", "chrisjoris@hotmail.com", tenMonthsFromNow));
		};
		
	}
}
