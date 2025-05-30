package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Repository.User.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService extends UserService<Admin, AdminRepository> {
    public AdminService(AdminRepository rep) {
        super(rep);
    }
}
