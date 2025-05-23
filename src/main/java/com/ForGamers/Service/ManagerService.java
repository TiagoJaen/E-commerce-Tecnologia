package com.ForGamers.Service;

import com.ForGamers.Model.User.Manager;
import com.ForGamers.Repository.ManagerRepository;
import com.ForGamers.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService extends UserService<Manager, ManagerRepository> {
    public ManagerService(ManagerRepository rep) {
        super(rep);
    }
}