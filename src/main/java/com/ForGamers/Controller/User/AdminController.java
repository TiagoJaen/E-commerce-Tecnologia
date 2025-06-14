package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.User;
import com.ForGamers.Service.User.AdminService;
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
@RequestMapping("/admins")
@AllArgsConstructor
@Getter
@Tag(name = "admins", description = "Operaciones relacionadas con los admins.")
public class AdminController {
    private AdminService services;

    //GET
    //Listado
    @Operation(summary = "Obtener listado de admins.", description = "Devuelve una lista de todos los admins.")
    @GetMapping("/all")
    public List<Admin> listAdmins() {
        return services.list();
    }

    //Obtener por id
    @Operation(summary = "Obtener un admin por id.")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Admin> admin = services.getById(id);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Obtener por usuario
    @Operation(summary = "Obtener un admin por user.")
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUserame(@PathVariable String username){
        Optional<Admin> admin = services.getByUsername(username);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @Operation(summary = "Agregar un admin.", description = "No incluir id al agregar un admin.")
    @PostMapping
    public ResponseEntity<?> addAdmin(@RequestBody AdminDTO dto) {
        try {
            Admin admin = new Admin(dto);
            admin.setRole(Role.ADMIN);
            services.add(admin);
            return ResponseEntity.ok(admin);
        }catch (ExistentEmailException | ExistentUsernameException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    //DELETE
    @Operation(summary = "Eliminar un admin por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteAdmin(@RequestParam Long id){
        return services.delete(id);
    }

    //PUT
    @Operation(summary = "Modificar un admin.")
    @PutMapping
    public ResponseEntity<?> updateAdmin(@RequestBody Admin updatedUser) {
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
                                               @RequestBody Admin updatedUser) {
        try {
            Admin currentAdmin = services.getByUsername(userDetails.getUsername()).get();
            services.modify(currentAdmin.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}