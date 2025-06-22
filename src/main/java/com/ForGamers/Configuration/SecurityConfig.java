package com.ForGamers.Configuration;

import com.ForGamers.Service.User.UserLookupService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Locale;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Schema(description = "Configuración de seguridad.")
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter, UserLookupService service) throws Exception {
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
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/products.html",
                                "/profile.html",
                                "/admins.html",
                                "/clients.html",
                                "/managers.html",

                                //Endpoints
                                "/",
                                "/user",
                                "/products/all",
                                "/products/paginated",
                                "/products/name/**",
                                "/products/id/**",
                                "/clients",
                                "/logout",
                                "/cart",
                                "/cart/total",
                                "/auth/**",
                                "/favicon.ico",
                                "/card",
                                "/card/id",
                                "/payment",
                                "/payment/id",
                                "/payment/client-historial"
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

                                // Swagger solo para admins
                                "/docs/**",
                                "/v3/api-docs/**",
                                "/js/swagger-config.js",
                                "/js/auth.js"

                        ).hasRole("ADMIN")
                        .requestMatchers(

                                "/clients/all",
                                "/clients/id/",
                                "/clients/paginated",
                                "/clients/username/"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider(service))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                //EXCEPTIONS
                http.exceptionHandling(exception -> exception
                        //Reemplazé la clase de UserAuthentication por esta función lambda que redirige en lugar de devolver texto
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/");
                        })
                );
                // Disable form login
                http.formLogin(AbstractHttpConfigurer::disable);
                http.logout(LogoutConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserLookupService service) {
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