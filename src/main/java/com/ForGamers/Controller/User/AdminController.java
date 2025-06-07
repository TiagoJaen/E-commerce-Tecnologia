package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Service.User.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            Admin admin = new Admin(dto);
            admin.setRole(Role.ADMIN);
            Admin saved = services.add(admin);
            return ResponseEntity.ok(saved);
        }catch (ExistentEmailException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }catch (ExistentUsernameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un admin por id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id){
        return services.delete(id);
    }

    @Operation(summary = "Obtener un admin por id.")
    @GetMapping("/{id}")
    public Admin getById(@PathVariable Long id){
        return services.getById(id);
    }

    @Operation(summary = "Editar un admin.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        return services.modify(id, updatedAdmin);
    }
}