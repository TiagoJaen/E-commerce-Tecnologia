package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.ManagerDTO;
import com.ForGamers.Model.User.User;
import com.ForGamers.Service.User.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    //GET
    //Listado
    @Operation(summary = "Obtener listado de gestores.", description = "Devuelve una lista de todos los gestores.")
    @GetMapping("/all")
    public List<Manager> listManagers() {
        return services.list();
    }

    //Obtener por id
    @Operation(summary = "Obtener un gestor por id.")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Manager> manager = services.getById(id);
        if (manager.isPresent()) {
            return ResponseEntity.ok(manager.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Obtener por usuario
    @Operation(summary = "Obtener un gestor por user.")
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUserame(@PathVariable String username){
        Optional<Manager> manager = services.getByUsername(username);
        if (manager.isPresent()) {
            return ResponseEntity.ok(manager.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @Operation(summary = "Agregar un gestor.", description = "No incluir id al agregar un gestor.")
    @PostMapping
    public ResponseEntity<?> addManager(@RequestBody ManagerDTO dto) {
        try {
            Manager manager = new Manager(dto);
            manager.setRole(Role.MANAGER);
            services.add(manager);
            return ResponseEntity.ok(manager);
        }catch (ExistentEmailException | ExistentUsernameException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    //DELETE
    @Operation(summary = "Eliminar un gestor por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteManager(@RequestParam Long id){
        return services.delete(id);
    }

    //PUT
    @Operation(summary = "Modificar un manager.")
    @PutMapping
    public ResponseEntity<?> updateManager(@RequestBody Manager updatedUser) {
        try {
            services.modify(updatedUser.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Peticiones del usuario de la sesión
    //GET
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return services.getByUsername(userDetails.getUsername()).get();
    }

    //PUT todavia no sé si funciona
    @PutMapping("/me/update")
    public ResponseEntity<?> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody Manager updatedUser) {
        try {
            Manager currentManager = services.getByUsername(userDetails.getUsername()).get();
            services.modify(currentManager.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}