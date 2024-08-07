package com.kauan.LightBarbershop.config;

import com.kauan.LightBarbershop.components.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login/barber").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/client").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register/barber").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register/client").permitAll()
                        .requestMatchers(HttpMethod.GET, "/barber/{uuid}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/barber/{uuid}/get-password").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/barber/{uuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/barber/{uuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/client/{uuid}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/client/{uuid}/get-password").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/client/{uuid}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/client/{uuid}").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 2, 65536, 2);
    }
}
