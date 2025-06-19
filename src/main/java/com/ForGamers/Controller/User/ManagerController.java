package com.ForGamers.Controller.User;

import com.ForGamers.Exception.EmailAlreadyExistsException;
import com.ForGamers.Exception.UsernameAlreadyExistsException;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.ManagerDTO;
import com.ForGamers.Service.User.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    //Paginación
    @Operation(summary = "Obtener listado de gestores con paginación.")
    @GetMapping("/paginated")
    public Page<Manager> listManagerPaginated(@RequestParam(name = "page") int page,
                                             @RequestParam(name = "size") int size) {
        return services.listManagersPaginated(page, size);
    }

    //Obtener por usuario
    @Operation(summary = "Obtener un gestor por username sin contar mayusculas (para barra de búsqueda).")
    @GetMapping("/username/{username}")
    public List<Manager> getByUsernameIgnoringCase(@PathVariable(name = "username", required = false) String username) {
        if (username == null || username.isEmpty()) {
            return listManagers();
        } else {
            return services.getByUsernameIgnoringCase(username);
        }
    }

    //Obtener por id
    @Operation(summary = "Obtener un gestor por id.")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id){
        Optional<Manager> manager = services.getById(id);
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
        }catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    //DELETE
    @Operation(summary = "Eliminar un gestor por id.")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable(name = "id") Long id){
        return services.delete(id);
    }

    //PUT
    @Operation(summary = "Modificar un manager.")
    @PutMapping
    public ResponseEntity<?> updateManager(@RequestBody Manager updatedUser) {
        try {
            services.modify(updatedUser.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}