package com.ForGamers.Service.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.UserRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Getter
@Schema(description = "Servicio generico para todos los tipos de usuarios.")
public class UserService<T extends User,R extends UserRepository<T>>{
    protected final R repository;

    public T add(T t) {
        return repository.save(t);
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

    public ResponseEntity<Void> modify(Long id, T t){
        if (repository.existsById(id)) {
            T old = repository.getReferenceById(id);
            old.setName(t.getName());
            old.setLastname(t.getLastname());
            old.setEmail(t.getEmail());
            old.setPhone(t.getPhone());
            old.setUsername(t.getUsername());
            old.setPassword(t.getPassword());

            repository.save(old);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public T getById(Long id){
        return repository.getReferenceById(id);
    }

    public Optional<T> getByUsername(String username) {
        return repository.getByUsername(username);
    }
}