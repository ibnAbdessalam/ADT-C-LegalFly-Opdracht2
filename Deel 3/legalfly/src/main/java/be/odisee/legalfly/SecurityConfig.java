package be.odisee.legalfly;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Voor eenvoud, zet CSRF uit (bij custom login)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // API endpoints zijn openbaar
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/jurist/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jurist/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/jurist/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/jurist/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/jurist/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/reports/save").permitAll()
                        .requestMatchers(HttpMethod.POST, "/debug/report").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/admin/**").permitAll()
                        .requestMatchers("admin/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // Disable default login mechanism
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}