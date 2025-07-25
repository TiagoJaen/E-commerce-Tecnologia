package com.ForGamers.Service.User;

import com.ForGamers.Configuration.SecurityConfig;
import com.ForGamers.Exception.EmailAlreadyExistsException;
import com.ForGamers.Exception.UsernameAlreadyExistsException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.Manager;
import com.ForGamers.Repository.User.ManagerRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Schema(description = "Servicio de gestores.")
@Service
public class ManagerService {
    private final ManagerRepository repository;
    private final UserLookupService userLookupService;
    private PasswordEncoder passwordEncoder;

    public void add(Manager t) {
        if (userLookupService.findByUsername(t.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }else if(userLookupService.findByEmail(t.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        t.setPassword(SecurityConfig.passwordEncoder().encode(t.getPassword()));
        repository.save(t);
    }

    public Page<Manager> listManagersPaginated(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Manager> getByUsernameIgnoringCase(String username) {
        return repository.getByUsernameContainingIgnoreCase(username);
    }

    public ResponseEntity<Void> delete(Long id){
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Manager> list() {
        return repository.findAll();
    }

    public void modify (Long id, Manager t){
        Manager old = repository.findById(id).get();
        //Verificar si cambió el usuario o el email
        if (!old.getEmail().equals(t.getEmail())) {
            //Verificar si el email nuevo ya se encuentra en uso
            if (userLookupService.findByEmail(t.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException();
            }
        }else if (!old.getUsername().equals(t.getUsername())) {
            //Verificar si el usuario nuevo ya se encuentra en uso
            if (userLookupService.findByUsername(t.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException();
            }
        }
        old.setName(t.getName());
        old.setLastname(t.getLastname());
        old.setEmail(t.getEmail());
        old.setPhone(t.getPhone());
        old.setUsername(t.getUsername());

        // Verificar si se ingresó una contraseña nueva, si el usuario no quiso cambiarla debe dejar ese input vacío.
        if (t.getPassword() != null && !t.getPassword().isBlank()) {
            String encoded = passwordEncoder.encode(t.getPassword());
            old.setPassword(encoded);
        }
        repository.save(old);
    }

    public Optional<Manager> getById(Long id){
        return repository.findById(id);
    }

    public Optional<Manager> getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public Optional<Manager> getByEmail(String email){
        return repository.getByEmail(email);
    }
}