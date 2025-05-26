package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Manager;
import com.ForGamers.Repository.User.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService extends UserService<Manager, ManagerRepository> {
    public ManagerService(ManagerRepository rep) {
        super(rep);
    }
}