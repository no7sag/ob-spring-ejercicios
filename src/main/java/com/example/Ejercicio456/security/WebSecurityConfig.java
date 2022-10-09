package com.example.Ejercicio456.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/saludo").permitAll()  // "Sin autenticación"
                .antMatchers("/api/laptops").hasRole("ADMIN")  // "Solo con el rol ADMIN"
                .anyRequest().authenticated() // "Con autenticación"
                .and().formLogin()
                .and().httpBasic();

        return http.build();
    }

    @Bean
    protected InMemoryUserDetailsManager configureAuthentication() {
        List<UserDetails> userDetails = new ArrayList<>();

        List<GrantedAuthority> employeeRoles = new ArrayList<>();
        employeeRoles.add(new SimpleGrantedAuthority("EMPLOYEE"));

        List<GrantedAuthority> adminRoles = new ArrayList<>();
        employeeRoles.add(new SimpleGrantedAuthority("ADMIN"));

        // Empleado:1234
        userDetails.add(new User(
                "Empleado",
                passwordEncoder().encode("1234"),
                employeeRoles));

        // Administrador:5678
        userDetails.add(new User(
                "Administrador",
                passwordEncoder().encode("5678"),
                adminRoles));

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall looseHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowBackSlash(true);  // Permitir "/" en la URL
        firewall.setAllowSemicolon(true);  // Permitir ";" en la URL
        return firewall;
    }

}
