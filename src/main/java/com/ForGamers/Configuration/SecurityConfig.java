package com.ForGamers.Configuration;

import com.ForGamers.Component.UserAuthentication;
import com.ForGamers.Service.User.GeneralUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Schema(description = "Configuracion de seguridad.")
public class SecurityConfig {
    @Autowired
    private UserAuthentication auth;

    @Autowired
    private GeneralUserService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                //PERMISOS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",

                                //Static
                                "/login.html",
                                "/index.html",
                                "/style.css",
                                "/script.js",
                                "/Media/**",
                                "/docs",

                                //Endpoints
                                "/products"


                        ).permitAll()
                        .requestMatchers("/client").hasAuthority("CLIENT")
                        .requestMatchers(
                                //Estos endpoints junto con los de Swagger, se tienen que pasar a rol de Admin
                                //cuando el login funcione
                                "/managers",
                                "/admins",
                                "/clients",
                                "/login",
                                "/admin",

                                // Swagger solo para admins
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).hasAuthority("ADMIN")
                        .anyRequest().authenticated()

                )
                //EXCEPTIONS
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(auth)
                )
                //FORM
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                );

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}