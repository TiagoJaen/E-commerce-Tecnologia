package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.UserRepository;
import com.ForGamers.Service.User.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "Operaciones para usuario de la sesión.")
public class UserController {
    private final GenericResponseService responseBuilder;
    private UserService<User, UserRepository<User>> userService;
    //GET
    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getByUsername(userDetails.getUsername()).get();
    }

    //PUT
    @PutMapping("/update")
    public ResponseEntity<?> updateCurrentUser(@RequestBody User updatedUser) {
        try {
            userService.modify(updatedUser.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
