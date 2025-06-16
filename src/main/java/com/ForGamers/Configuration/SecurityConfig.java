package com.ForGamers.Configuration;

import com.ForGamers.Service.User.UserLookupService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Schema(description = "Configuración de seguridad.")
public class SecurityConfig {
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
                                "/user",
                                "/products/all",
                                "/products/paginated",
                                "/products/name/**",
                                "/products/id/**",
                                "/clients",
                                "/login",
                                "/logout",
                                "/cart",
                                "/favicon.ico"
                        ).permitAll()
                        .requestMatchers("/client").hasRole("CLIENT")
                        .requestMatchers("/manager").hasRole("MANAGER")
                        .requestMatchers(
                                "/managers",
                                "/managers/username/",
                                "/managers/all",
                                "/managers/id/",
                                "/managers/paginated",
                                "/admins",
                                "/admins/username/",
                                "/admins/all",
                                "/admins/id/",
                                "/admins/paginated",
                                "/admin",
                                "/user/update/any",

                                // Swagger solo para admins
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/products.html",
                                "/clients/all",
                                "/clients/id/",
                                "/clients/paginated",
                                "/clients/username/"
                                ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                "/user/update"
                        ).hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .anyRequest().authenticated()
                )
                //EXCEPTIONS
                .exceptionHandling(exception -> exception
                        //Reemplazé la clase de UserAuthentication por esta función lambda que redirige en lugar de devolver texto
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/");
                        })
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