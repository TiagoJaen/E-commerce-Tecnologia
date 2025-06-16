package com.ForGamers.Service.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Exception.WrongPasswordException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.AdminRepository;
import com.ForGamers.Repository.User.ClientRepository;
import com.ForGamers.Repository.User.ManagerRepository;
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

    public Optional<? extends User> findByEmail(String email) {
        Optional<Client> client = clientRepository.getByEmail(email);
        if (client.isPresent()) return client;

        Optional<Manager> manager = managerRepository.getByEmail(email);
        if (manager.isPresent()) return manager;

        return adminRepository.getByEmail(email);
    }

    public void modify (Long id, User t){
        User old = findById(id).get();
        //Verificar si cambió el usuario o el email
        if (!old.getEmail().equals(t.getEmail())) {
            //Verificar si el email nuevo ya se encuentra en uso
            if (findByEmail(t.getEmail()).isPresent()) {
                throw new ExistentEmailException();
            }
        }else if (!old.getUsername().equals(t.getUsername())) {
            //Verificar si el usuario nuevo ya se encuentra en uso
            if (findByUsername(t.getUsername()).isPresent()) {
                throw new ExistentUsernameException();
            }
        }
        if (old.getRole() == Role.ADMIN){
            Admin admin = new Admin(t);
            adminRepository.save(admin);
        }else if (old.getRole() == Role.MANAGER){
            Manager manager = new Manager(t);
            managerRepository.save(manager);
        }else if (old.getRole() == Role.CLIENT){
            Client client = new Client(t);
            clientRepository.save(client);
        }
    }

    public void deleteCurrentUser(String username, String passwordCheck) {
        User user = findByUsername(username).get();
            //Si la contraseña ingresada coincide con la encriptada
            if (passwordEncoder.matches(passwordCheck, user.getPassword())) {
                if (user.getRole() == Role.ADMIN){
                    Admin admin = new Admin(user);
                    adminRepository.delete(admin);
                }else if (user.getRole() == Role.MANAGER){
                    Manager manager = new Manager(user);
                    managerRepository.delete(manager);
                }else if (user.getRole() == Role.CLIENT){
                    Client client = new Client(user);
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
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRole().toString())
                .build();
    }
}
