package com.ForGamers.Component;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Service.User.AdminService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserInit {
    private final AdminService repository;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void createSuperUser() {
        Optional<Admin> op = repository.getByUsername("jefe_maestro");
        if(op.isEmpty()) {
            Admin admin = new Admin(
                    "Jefe",
                    "Maestro",
                    "jefemaestro@gmail.com",
                    "2237984567",
                    "jefe_maestro",
                    encoder.encode("Admin123!")
            );
            repository.add(admin);
        }
    }
}
