package com.ForGamers.Component;

import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.ManagerDTO;
import com.ForGamers.Service.User.ManagerService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ManagerInit {
    private final ManagerService managerService;

    @PostConstruct
    public void createSuperUser() {
        if(managerService.list().isEmpty()){
            ManagerDTO dto = new ManagerDTO(
                    "Lautarani",
                    "Lorenzani",
                    "lorenzani@gmail.com",
                    "2237984567",
                    "manager",
                    "123"
            );
            Manager manager = new Manager(dto);
            managerService.add(manager);
        }
    }
}