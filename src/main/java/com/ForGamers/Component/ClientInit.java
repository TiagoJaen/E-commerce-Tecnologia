package com.ForGamers.Component;

import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.ClientDTO;
import com.ForGamers.Service.User.ClientService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientInit {
    private final ClientService service;

    private void createClient(Client client) {
        if(service.getByUsername(client.getUsername()).isEmpty()) {
            service.add(client);
        }
    }

    @PostConstruct
    public void createInitClients() {
        if(service.getByUsername("qwe").isEmpty()) {
            service.add(service.DTOtoClient(new ClientDTO(
                    "rivato",
                    "aravir",
                    "client1@gmail.com",
                    "2237984568",
                    "qwe",
                    "123"
            )));
        }
    }
}