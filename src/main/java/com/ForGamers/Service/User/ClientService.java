package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Client;
import com.ForGamers.Repository.User.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends UserService<Client, ClientRepository> {
    public ClientService(ClientRepository rep, UserLookupService userLookupService) {
        super(rep, userLookupService);
    }
}