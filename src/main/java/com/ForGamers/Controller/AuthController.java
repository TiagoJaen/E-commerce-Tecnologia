package com.ForGamers.Controller;

import com.ForGamers.Model.LoginRequest;
import com.ForGamers.Model.LoginResponse;
import com.ForGamers.Service.JwtService;
import com.ForGamers.Service.User.UserLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Define que esta clase manejará peticiones HTTP
@RequestMapping("/auth") // El endpoint completo será /auth/login
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserLookupService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        // Autenticamos al usuario con nombre y contraseña
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Obtenemos los detalles del usuario desde la base de datos
        UserDetails user = service.loadUserByUsername(request.getUsername());

        // Generamos el token JWT
        String token = jwtService.generateToken(user);

        // Devolvemos el token en la respuesta
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
