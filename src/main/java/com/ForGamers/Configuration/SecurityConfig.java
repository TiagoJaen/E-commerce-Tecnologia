package com.ForGamers.Configuration;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Schema(description = "Configuracion de seguridad.")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
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

                                // Swagger
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",

                                //Endpoints
                                "/products",
                                //Estos endpoints junto con los de Swagger, se tienen que pasar a rol de Admin
                                //cuando el login funcione
                                "/managers",
                                "/admins",
                                "/clients",
                                "/login"

                        ).permitAll()
                        .requestMatchers("/client").hasAuthority("CLIENT")
                        .requestMatchers(
                                "/admin"
                        ).hasAuthority("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login.html")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                );

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource data) {
        return new JdbcUserDetailsManager(data);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService createUser(PasswordEncoder encoder) {
//        var user = User.withUsername("admin")
//                .password(encoder.encode("1234"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
