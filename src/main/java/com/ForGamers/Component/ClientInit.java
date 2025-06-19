package com.ForGamers.Component;

import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.ClientDTO;
import com.ForGamers.Service.User.ClientService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        if(service.list().isEmpty()){
            ClientDTO dto = new ClientDTO(
                    "rivato",
                    "aravir",
                    "client1@gmail.com",
                    "2237984568",
                    "qwe",
                    "123"
            );
            service.add(new Client(dto));
        }
    }
}