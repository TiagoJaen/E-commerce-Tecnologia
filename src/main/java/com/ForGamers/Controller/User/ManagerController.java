package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.ManagerDTO;
import com.ForGamers.Service.User.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/managers")
@AllArgsConstructor
@Getter
@Tag(name = "managers", description = "Operaciones relacionadas con los gestores.")
public class ManagerController {
    private ManagerService services;

    @Operation(summary = "Obtener listado de gestores.", description = "Devuelve una lista de todos los gestores.")
    @GetMapping
    public List<Manager> listManagers() {
        return services.list();
    }

    @Operation(summary = "Agregar un gestor.", description = "No incluir id al agregar un gestor.")
    @PostMapping
    public ResponseEntity<?> addManager(@RequestBody ManagerDTO dto) {
        try {
            return ResponseEntity.ok(services.add(services.DTOtoManager(dto)));
        }catch (ExistentEmailException | ExistentUsernameException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @Operation(summary = "Eliminar un gestor por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteManager(@RequestParam Long id){
        return services.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener un gestor por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id") Long id){
        Optional<Manager> manager = services.getById(id);
        if (manager.isPresent()) {
            return ResponseEntity.ok(manager.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener un gestor por user.")
    @GetMapping(value = "/username", params = "username")
    public ResponseEntity<?> getByUserame(@RequestParam(name = "username") String username){
        Optional<Manager> manager = services.getByUsername(username);
        if (manager.isPresent()) {
            return ResponseEntity.ok(manager.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Editar un gestor.")
    @PutMapping
    public ResponseEntity<Void> modifyManager(@RequestBody Manager updatedManager) {
        return services.modify(updatedManager.getId(), updatedManager);
    }
}