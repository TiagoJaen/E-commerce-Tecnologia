package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Repository.User.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AdminService extends UserService<Admin, AdminRepository> {
    public AdminService(AdminRepository rep, UserLookupService userLookupService, PasswordEncoder encoder) {
        super(rep, userLookupService, encoder);
    }

    public Admin DTOtoAdmin(AdminDTO dto) {
        return new Admin(
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