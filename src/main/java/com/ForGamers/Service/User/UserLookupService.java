package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.AdminRepository;
import com.ForGamers.Repository.User.ClientRepository;
import com.ForGamers.Repository.User.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Servicio de búsqueda en todas las tablas de usuario (clientes, administradores y gestores)
@Service
public class UserLookupService {
    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;

    public UserLookupService(ClientRepository clientRepository,
                             ManagerRepository managerRepository,
                             AdminRepository adminRepository) {
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
    }

    public Optional<? extends User> findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) return client;

        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isPresent()) return manager;

        return adminRepository.findById(id);
    }

    public Optional<? extends User> findByUsername(String username) {
        Optional<Client> client = clientRepository.getByUsername(username);
        if (client.isPresent()) return client;

        Optional<Manager> manager = managerRepository.getByUsername(username);
        if (manager.isPresent()) return manager;

        return adminRepository.getByUsername(username);
    }
}
