package com.ForGamers.Component;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Service.User.AdminService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AdminInit {
    private final AdminService adminService;

    @PostConstruct
    public void createSuperUser() {
        if(adminService.list().isEmpty()){
            AdminDTO dto = new AdminDTO(
                    "Jefe",
                    "Maestro",
                    "admin@gmail.com",
                    "2237984567",
                    "admin",
                    "123"
            );
            Admin admin = new Admin(dto);
            adminService.add(admin);
        }
    }
}