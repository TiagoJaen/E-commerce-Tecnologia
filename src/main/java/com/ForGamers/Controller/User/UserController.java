package com.ForGamers.Controller.User;

import com.ForGamers.Model.User.User;
import com.ForGamers.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userService.getByUsername(userDetails.getUsername()).get();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestBody User updatedUser) {
        userService.modify(updatedUser.getId(), updatedUser);
        return ResponseEntity.ok("Datos actualizados");
    }
}
