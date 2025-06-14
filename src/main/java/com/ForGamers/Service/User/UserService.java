package com.ForGamers.Service.User;

import com.ForGamers.Configuration.SecurityConfig;
import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.UserRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Getter
@Schema(description = "Servicio genérico para todos los tipos de usuarios.")
public class UserService<T extends User,R extends UserRepository<T>>{
    protected final R repository;
    protected final UserLookupService userLookupService;

    public void add(T t) {
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

    public List<T> list() {
        return repository.findAll();
    }

    public void modify (Long id, T t){
        T old = repository.findById(id).get();
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

    public Optional<T> getById(Long id){
        return repository.findById(id);
    }

    public Optional<T> getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public Optional<T> getByEmail(String email){
        return repository.getByEmail(email);
    }
}