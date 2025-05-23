package com.ForGamers.Service;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.ProductRepository;
import com.ForGamers.Repository.UserRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Schema(description = "Servicio generico para todos los tipos de usuarios.")
public class UserService<T extends User> {
    private final UserRepository<T> userRepository;

    public UserService(UserRepository<T> userRepository) {
        this.userRepository = userRepository;
    }

    public T addUser(T t) {
        return userRepository.save(t);
    }

    public ResponseEntity<Void> deleteUser(Long id){
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<T> listProducts() {
        return userRepository.findAll();
    }

    public ResponseEntity<Void> modifyProduct(Long id, T t){
        if (userRepository.existsById(id)) {
            T old = userRepository.getReferenceById(id);
            old.setName(t.getName());
            old.setLastname(t.getLastname());
            old.setEmail(t.getEmail());
            old.setPhone(t.getPhone());
            old.setUsername(t.getUsername());
            old.setPassword(t.getPassword());

            userRepository.save(old);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public T getById(Long id){
        return userRepository.getReferenceById(id);
    }
}