package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.User;
import com.ForGamers.Service.User.UserLookupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Getter
@Tag(name = "users", description = "Operaciones relacionadas con el usuario de la sesión")
public class UserController {
    private UserLookupService service;

    //Peticiones del usuario de la sesión
    //GET
    @GetMapping
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay un usuario autenticado.");
        }
        return ResponseEntity.ok(
                service.findByUsername(userDetails.getUsername()).get()
                );
    }

    //PUT todavia no sé si funciona
    @PutMapping
    public ResponseEntity<?> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody User updatedUser) {
        try {
            User currentUser = service.findByUsername(userDetails.getUsername()).get();
            service.modify(currentUser.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
