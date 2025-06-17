package com.ForGamers.Service.User;

import com.ForGamers.Configuration.SecurityConfig;
import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Repository.User.AdminRepository;
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
@Schema(description = "Servicio de admins.")
@Service
public class AdminService {
    private final AdminRepository repository;
    private final UserLookupService userLookupService;
    private PasswordEncoder passwordEncoder;

    public void add(Admin t) {
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

    public List<Admin> list() {
        return repository.findAll();
    }

    public Page<Admin> listAdminsPaginated(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Admin> getByUsernameIgnoringCase(String username) {
        return repository.getByUsernameContainingIgnoreCase(username);
    }

    public void modify (Long id, Admin t){
        Admin old = repository.findById(id).get();
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

        // Verificar si se ingresó una contraseña nueva, si el usuario no quiso cambiarla debe dejar ese input vacío.
        if (t.getPassword() != null && !t.getPassword().isBlank()) {
            String encoded = passwordEncoder.encode(t.getPassword());
            old.setPassword(encoded);
        }

        repository.save(old);
    }

    public Optional<Admin> getById(Long id){
        return repository.findById(id);
    }

    public Optional<Admin> getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public Optional<Admin> getByEmail(String email){
        return repository.getByEmail(email);
    }
}
