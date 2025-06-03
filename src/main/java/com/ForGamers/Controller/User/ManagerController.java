package com.ForGamers.Controller.User;

import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Service.User.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public Manager addManager(@RequestBody Manager manager) {
        manager.setRole(Role.MANAGER);
        return services.add(manager);
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