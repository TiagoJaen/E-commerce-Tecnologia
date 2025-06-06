package com.ForGamers.Component;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
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
    private final AdminService adminService;

    @PostConstruct
    public void createSuperUser() {
        Optional<Admin> op = adminService.getByUsername("jefe_maestro");
        AdminDTO dto = new AdminDTO(
                "Jefe",
                "Maestro",
                "jefemaestro@gmail.com",
                "2237984567",
                "jefe_maestro",
                "Admin123!"
        );
        if(op.isEmpty()) {
            Admin admin = new Admin(dto);
            adminService.add(admin);
        }
    }
}