package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.AdminDTO;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.ClientDTO;
import com.ForGamers.Repository.User.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends UserService<Client, ClientRepository> {
    public ClientService(ClientRepository rep, UserLookupService userLookupService, PasswordEncoder encoder) {
        super(rep, userLookupService, encoder);
    }

    public Client DTOtoClient(ClientDTO dto) {
        return new Client(
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