package com.ForGamers.Controller.User;

import com.ForGamers.Exception.EmailAlreadyExistsException;
import com.ForGamers.Exception.UsernameAlreadyExistsException;
import com.ForGamers.Model.User.*;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Security.UserDetailsImpl;
import com.ForGamers.Service.JwtService;
import com.ForGamers.Service.User.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    private JwtService jwtService;

    //GET
    //Listado
    @Operation(summary = "Obtener listado de admins.", description = "Devuelve una lista de todos los admins.")
    @GetMapping("/all")
    public List<Admin> listAdmins() {
        return services.list();
    }

    //Paginación
    @Operation(summary = "Obtener listado de gestores con paginación.")
    @GetMapping("/paginated")
    public Page<Admin> listAdminPaginated(@RequestParam(name = "page") int page,
                                              @RequestParam(name = "size") int size) {
        return services.listAdminsPaginated(page, size);
    }

    //Obtener por usuario
    @Operation(summary = "Obtener un admin por username sin contar mayusculas (para barra de búsqueda).")
    @GetMapping("/username/{username}")
    public List<Admin> getByUsernameIgnoringCase(@PathVariable(name = "username", required = false) String username) {
        if (username == null || username.isEmpty()) {
            return listAdmins();
        } else {
            return services.getByUsernameIgnoringCase(username);
        }
    }
    //Obtener por id
    @Operation(summary = "Obtener un admin por id.")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id){
        Optional<Admin> admin = services.getById(id);
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
        }catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    //DELETE
    @Operation(summary = "Eliminar un admin por id.")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable(name = "id") Long id){
        return services.delete(id);
    }

    //PUT
    @Operation(summary = "Modificar un admin.")
    @PutMapping
    public ResponseEntity<?> updateAdmin(@RequestBody Admin updatedUser, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if(token != null && token.startsWith("Bearer ")){
                token = token.substring(7);
            }

            String currentUsername = jwtService.extractUsername(token);
            Admin currentAdmin = services.getByUsername(currentUsername).get();
            updatedUser.setRole(Role.ADMIN);
            services.modify(updatedUser.getId(), updatedUser);

            if(currentAdmin.getId().equals(updatedUser.getId())){
                UserDetailsImpl updatedDetails = new UserDetailsImpl(updatedUser);
                String newToken = jwtService.generateToken(updatedDetails);
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + newToken)
                        .body(updatedUser);
            }
            return ResponseEntity.ok(updatedUser);
        }catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}