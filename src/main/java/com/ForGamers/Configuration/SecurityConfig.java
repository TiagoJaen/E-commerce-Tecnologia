package com.ForGamers.Configuration;

import com.ForGamers.Component.UserAuthentication;
import com.ForGamers.Service.User.GeneralUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Schema(description = "Configuración de seguridad.")
public class SecurityConfig {
    @Autowired
    private UserAuthentication auth;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter, GeneralUserService service) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //PERMISOS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(

                                //Static
                                "/static/**",
                                "/login.html",
                                "/index.html",
                                "/products.html",
                                "/css/**",
                                "/css/style.css",
                                "/css/products-style.css",
                                "/css/login-style.css",
                                "/js/**",
                                "/js/script.js",
                                "/js/login-script.js",
                                "/js/products-script.js",
                                "/Media/**",
                                "/docs.html",

                                //Endpoints
                                "/",
                                "/products",
                                "/logout",
                                "/clients",
                                "/cart",
                                "/auth/**"
                        ).permitAll()
                        .requestMatchers("/client").hasRole("CLIENT")
                        .requestMatchers("/manager").hasRole("MANAGER")
                        .requestMatchers(
                                "/managers",
                                "/admins",
                                "/admin",

                                // Swagger solo para admins
                                "/docs/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider(service))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                //EXCEPTIONS
                http.exceptionHandling(exception -> exception
                        .authenticationEntryPoint(auth)
                );
                // Disable form login
                http.formLogin(AbstractHttpConfigurer::disable);
                http.logout(LogoutConfigurer::disable);

        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(GeneralUserService service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}