package com.ForGamers.Service;

import com.ForGamers.Model.User.Client;
import com.ForGamers.Repository.ClientRepository;
import com.ForGamers.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends UserService<Client, ClientRepository> {

    public ClientService(ClientRepository rep) {
        super(rep);
    }
}