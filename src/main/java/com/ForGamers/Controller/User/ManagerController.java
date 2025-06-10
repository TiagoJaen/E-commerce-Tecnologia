package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.ManagerDTO;
import com.ForGamers.Service.User.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            Manager manager = new Manager(dto);
            manager.setRole(Role.MANAGER);
            Manager saved = services.add(manager);
            return ResponseEntity.ok(saved);
        }catch (ExistentEmailException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }catch (ExistentUsernameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un gestor por id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id){
        return services.delete(id);
    }

    @Operation(summary = "Obtener un gestor por id.")
    @GetMapping("/{id}")
    public Manager getById(@PathVariable Long id){
        return services.getById(id);
    }

    @Operation(summary = "Editar un gestor.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyManager(@PathVariable Long id, @RequestBody Manager updatedManager) {
        return services.modify(id, updatedManager);
    }
}