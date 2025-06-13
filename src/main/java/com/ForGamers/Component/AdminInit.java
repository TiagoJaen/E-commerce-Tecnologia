package com.ForGamers.Component;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Client;
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
        if(adminService.getByUsername("admin").isEmpty()) {
            adminService.add(adminService.DTOtoAdmin(new AdminDTO(
                    "Jefe",
                    "Maestro",
                    "admin@gmail.com",
                    "2237984567",
                    "admin",
                    "123"
            )));
        }
    }
}