package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Service.User.AdminService;
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
@RequestMapping("/admins")
@AllArgsConstructor
@Getter
@Tag(name = "admins", description = "Operaciones relacionadas con los admins.")
public class AdminController {
    private AdminService services;

    @Operation(summary = "Obtener listado de admins.", description = "Devuelve una lista de todos los admins.")
    @GetMapping
    public List<Admin> listAdmins() {
        return services.list();
    }

    @Operation(summary = "Agregar un admin.", description = "No incluir id al agregar un admin.")
    @PostMapping
    public ResponseEntity<?> addAdmin(@RequestBody AdminDTO dto) {
        try {
            return ResponseEntity.ok(services.add(services.DTOtoAdmin(dto)));
        }catch (ExistentEmailException | ExistentUsernameException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @Operation(summary = "Eliminar un admin por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteAdmin(@RequestParam Long id){
        return services.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener un admin por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id") Long id){
        Optional<Admin> admin = services.getById(id);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener un admin por user.")
    @GetMapping(value = "/name", params = "username")
    public ResponseEntity<?> getByUserame(@RequestParam(name = "name") String username){
        Optional<Admin> admin = services.getByUsername(username);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Editar un admin.")
    @PutMapping
    public ResponseEntity<Void> modifyAdmin(@RequestBody Admin updatedAdmin) {
        return services.modify(updatedAdmin.getId(), updatedAdmin);
    }
}