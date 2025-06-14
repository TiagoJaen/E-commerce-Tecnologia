package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Repository.User.AdminRepository;
import org.springframework.stereotype.Service;


@Service
public class AdminService extends UserService<Admin, AdminRepository> {
    public AdminService(AdminRepository rep, UserLookupService userLookupService) {
        super(rep, userLookupService);
    }
}
