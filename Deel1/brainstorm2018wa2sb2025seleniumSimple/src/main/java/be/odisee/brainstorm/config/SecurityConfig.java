package be.odisee.brainstorm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * The default PasswordEncoder is now DelegatingPasswordEncoder which is a non-passive change.
     * This change ensures that passwords are now encoded using BCrypt by default
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Order(2) // AS chain should have HIGHEST_PRECEDENCE; this one runs after it
    SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        var requestCache = new HttpSessionRequestCache();
        var successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        // optionally: successHandler.setDefaultTargetUrl("/"); // fallback if no saved request

        http
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/login","/login/**", "/default-ui.css", "/favicon.ico", "/css/**", "/js/**", "/images/**", "/ask",  "/api/ask", "/ask").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .requestCache(rc -> rc.requestCache(requestCache))
                .formLogin(withDefaults())
                .logout(l -> l
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(csrf -> csrf
                        //.ignoringRequestMatchers("/oauth2/**", "/h2-console/**",  "/logout")
                        // /login werd toegevoegd, niet omdat het nodig is voor front-to-back auth, maar om backend auth te kunnen demonstreren
                        .ignoringRequestMatchers("/oauth2/**", "/h2-console/**", "/api/mock/**", "/logout", "/login", "/ask",  "/api/ask", "/**/ask")
                )
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation(sf -> sf.none()))
                .securityContext(sc -> sc.securityContextRepository(new HttpSessionSecurityContextRepository()));


        // .cors(withDefaults()) is usually unnecessary behind same-origin nginx; keep only if you really need it
        // http.cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("admin")
                .password("password")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
