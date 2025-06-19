package com.ForGamers.Service.User;

import com.ForGamers.Exception.EmailAlreadyExistsException;
import com.ForGamers.Exception.UsernameAlreadyExistsException;
import com.ForGamers.Exception.WrongPasswordException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.AdminRepository;
import com.ForGamers.Repository.User.ClientRepository;
import com.ForGamers.Repository.User.ManagerRepository;
import com.ForGamers.Security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Servicio de búsqueda en todas las tablas de usuario (clientes, administradores y gestores)
@AllArgsConstructor
@Service
public class UserLookupService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public Optional<? extends User> findById(Long id, Role role) {
        return switch (role){
            case CLIENT -> clientRepository.findById(id);
            case MANAGER -> managerRepository.findById(id);
            case ADMIN -> adminRepository.findById(id);
        };
    }

    public Optional<? extends User> findByUsername(String username) {
        Optional<Client> client = clientRepository.getByUsername(username);
        if (client.isPresent()) return client;

        Optional<Manager> manager = managerRepository.getByUsername(username);
        if (manager.isPresent()) return manager;

        return adminRepository.getByUsername(username);
    }

    public Optional<? extends User> findByEmail(String email) {
        Optional<Client> client = clientRepository.getByEmail(email);
        if (client.isPresent()) return client;

        Optional<Manager> manager = managerRepository.getByEmail(email);
        if (manager.isPresent()) return manager;

        return adminRepository.getByEmail(email);
    }

    public Role modify (User updated){
        User old = findById(updated.getId(), updated.getRole()).get();

        //Verificar si cambió el usuario o el email
        if (!old.getEmail().equals(updated.getEmail())) {
            //Verificar si el email nuevo ya se encuentra en uso
            if (findByEmail(updated.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException();
            }
        }else if (!old.getUsername().equals(updated.getUsername())) {
            //Verificar si el usuario nuevo ya se encuentra en uso
            if (findByUsername(updated.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException();
            }
        }

        if (old.getRole() == Role.ADMIN){
            Admin admin = (Admin) old;
            admin.setName(updated.getName());
            admin.setLastname(updated.getLastname());
            admin.setEmail(updated.getEmail());
            admin.setUsername(updated.getUsername());
            admin.setPhone(updated.getPhone());
            admin.setId(updated.getId());
            //Si se cambió la contraseña, es decir si no está vacía
            if (!updated.getPassword().isBlank() || !updated.getPassword().isEmpty()) {
                admin.setPassword(passwordEncoder.encode(updated.getPassword()));
            }
            adminRepository.save(admin);
        }else if (old.getRole() == Role.MANAGER){
            Manager manager = (Manager) old;
            manager.setName(updated.getName());
            manager.setLastname(updated.getLastname());
            manager.setEmail(updated.getEmail());
            manager.setUsername(updated.getUsername());
            manager.setPhone(updated.getPhone());
            manager.setId(updated.getId());
            //Si se cambió la contraseña, es decir si no está vacía
            if (!updated.getPassword().isBlank() || !updated.getPassword().isEmpty()) {
                manager.setPassword(passwordEncoder.encode(updated.getPassword()));
            }
            managerRepository.save(manager);
        }else if (old.getRole() == Role.CLIENT){
            Client client = (Client) old;
            client.setName(updated.getName());
            client.setLastname(updated.getLastname());
            client.setEmail(updated.getEmail());
            client.setUsername(updated.getUsername());
            client.setPhone(updated.getPhone());
            client.setId(updated.getId());
            //Si se cambió la contraseña, es decir si no está vacía
            if (!updated.getPassword().isBlank() || !updated.getPassword().isEmpty()) {
                client.setPassword(passwordEncoder.encode(updated.getPassword()));
            }
            System.out.println("AAAAAAAAAAAAAAA" + client.toString());
            clientRepository.save(client);
        }
        return old.getRole();
    }

    public void deleteCurrentUser(String username, String passwordCheck) {
        User user = findByUsername(username).get();
        //Si la contraseña ingresada coincide con la encriptada
        if (passwordEncoder.matches(passwordCheck, user.getPassword())) {
            if (user.getRole() == Role.ADMIN){
                Admin admin = (Admin)user;
                adminRepository.delete(admin);
            }else if (user.getRole() == Role.MANAGER){
                Manager manager = (Manager)user;
                managerRepository.delete(manager);
            }else if (user.getRole() == Role.CLIENT){
                Client client = (Client)user;
                clientRepository.delete(client);
            }
        }else{
            throw new WrongPasswordException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserDetailsImpl(u);
    }
}
