package com.ForGamers.Service.User;

import com.ForGamers.Configuration.SecurityConfig;
import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Repository.User.ClientRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Schema(description = "Servicio de clientes.")
@Service
public class ClientService {
    private final ClientRepository repository;
    private final UserLookupService userLookupService;

    public void add(Client t) {
        if (userLookupService.findByUsername(t.getUsername()).isPresent()) {
            throw new ExistentUsernameException();
        }else if(userLookupService.findByEmail(t.getEmail()).isPresent()) {
            throw new ExistentEmailException();
        }
        t.setPassword(SecurityConfig.passwordEncoder().encode(t.getPassword()));
        repository.save(t);
    }

    public ResponseEntity<Void> delete(Long id){
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Client> list() {
        return repository.findAll();
    }

    public void modify (Long id, Client t){
        Client old = repository.findById(id).get();
        //Verificar si cambió el usuario o el email
        if (!old.getEmail().equals(t.getEmail())) {
            //Verificar si el email nuevo ya se encuentra en uso
            if (userLookupService.findByEmail(t.getEmail()).isPresent()) {
                throw new ExistentEmailException();
            }
        }else if (!old.getUsername().equals(t.getUsername())) {
            //Verificar si el usuario nuevo ya se encuentra en uso
            if (userLookupService.findByUsername(t.getUsername()).isPresent()) {
                throw new ExistentUsernameException();
            }
        }
        old.setName(t.getName());
        old.setLastname(t.getLastname());
        old.setEmail(t.getEmail());
        old.setPhone(t.getPhone());
        old.setUsername(t.getUsername());
        old.setPassword(t.getPassword());
        repository.save(old);
    }

    public Optional<Client> getById(Long id){
        return repository.findById(id);
    }

    public Optional<Client> getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public Optional<Client> getByEmail(String email){
        return repository.getByEmail(email);
    }
}