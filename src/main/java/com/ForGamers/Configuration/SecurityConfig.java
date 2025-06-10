package com.ForGamers.Configuration;

import com.ForGamers.Component.UserAuthentication;
//import com.ForGamers.Service.User.GeneralUserService;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.UserRepository;
import com.ForGamers.Service.User.UserLookupService;
import com.ForGamers.Service.User.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
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
@Schema(description = "Configuración de seguridad.")
public class SecurityConfig {
    @Autowired
    private UserAuthentication auth;

    @Autowired
    private UserLookupService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
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

                                //Endpoints
                                "/",
                                "/products",
                                "/login",
                                "/logout",
                                "/clients",
                                "/cart"
                        ).permitAll()
                        .requestMatchers("/client").hasRole("CLIENT")
                        .requestMatchers("/manager").hasRole("MANAGER")
                        .requestMatchers(
                                "/managers",
                                "/admins",
                                "/admin",

                                // Swagger solo para admins
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                //EXCEPTIONS
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(auth)
                )
                //LOGIN Y LOGOUT
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login.html?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();

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