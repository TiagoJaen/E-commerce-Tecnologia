package com.ForGamers.Service.User;

import com.ForGamers.Model.User.*;
import com.ForGamers.Repository.User.ManagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService extends UserService<Manager, ManagerRepository> {
    public ManagerService(ManagerRepository rep, UserLookupService userLookupService, PasswordEncoder encoder) {
        super(rep, userLookupService, encoder);
    }

    public Manager DTOtoManager(ManagerDTO dto) {
        return new Manager(
                dto.getId(),
                dto.getName(),
                dto.getLastname(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getUsername(),
                encoder.encode(dto.getPassword())
        );
    }
}