package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Exception.WrongPasswordException;
import com.ForGamers.Model.User.User;
import com.ForGamers.Service.User.UserLookupService;
import io.swagger.v3.oas.annotations.Operation;
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
            //Devuelve stado 401 si no hay sesión.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(
                service.findByUsername(userDetails.getUsername()).get()
                );
    }

    //PUT
    @PutMapping
    public ResponseEntity<?> updateCurrentUser(@RequestBody User updatedUser) {
        try {
            service.modify(updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE
    @Operation(summary = "Darse de baja.")
    @DeleteMapping("/{password}")
    public ResponseEntity<?> deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails
                                                ,@PathVariable(name = "password") String password){
        try {
            service.deleteCurrentUser(userDetails.getUsername(), password);
            return ResponseEntity.ok().build();
        }catch (WrongPasswordException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
