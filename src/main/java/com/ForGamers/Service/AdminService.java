package com.ForGamers.Service;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Repository.AdminRepository;
import com.ForGamers.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends UserService<Admin, AdminRepository>{
    public AdminService(AdminRepository rep) {
        super(rep);
    }
}
