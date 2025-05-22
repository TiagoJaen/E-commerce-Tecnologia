package com.ForGamers.Controller;

import com.ForGamers.Model.User;
import com.ForGamers.Repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Operation(summary = "Crear Usuario.", description = "Crea un Usuario y lo devuelve.")
    public User cargar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto usuario a crear")
            @org.springframework.web.bind.annotation.RequestBody User usuario
    ) {

        return usuarioRepository.save(usuario);
    }

    @GetMapping
    @Operation(summary = "Obtener Usuarios.", description = "Busca todos los usuarios cargados en la base de datos.")
    public List<User> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Devuelve un  Usuario.")
    @Parameter(description = "Id del Usuario.", example = "1")
    @Operation(summary = "Buscar un Usuario.", description = "Busca un Usuario en particular")
    public ResponseEntity<User> buscarXid(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
