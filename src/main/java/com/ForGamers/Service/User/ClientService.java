package com.ForGamers.Service.User;

import com.ForGamers.Configuration.SecurityConfig;
import com.ForGamers.Exception.EmailAlreadyExistsException;
import com.ForGamers.Exception.UsernameAlreadyExistsException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Repository.Product.CartRepository;
import com.ForGamers.Repository.User.ClientRepository;
import com.ForGamers.Service.Product.CartService;
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
@Schema(description = "Servicio de clientes.")
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserLookupService userLookupService;
    private PasswordEncoder passwordEncoder;

    public void add(Client t) {
        if (userLookupService.findByUsername(t.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }else if(userLookupService.findByEmail(t.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        t.setPassword(SecurityConfig.passwordEncoder().encode(t.getPassword()));
        clientRepository.save(t);
    }

    public ResponseEntity<Void> delete(Long id){
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Client> list() {
        return clientRepository.findAll();
    }

    public Page<Client> listClientsPaginated(int page, int size) {
        return clientRepository.findAll(PageRequest.of(page, size));
    }

    public void modify (Long id, Client t){
        Client old = clientRepository.findById(id).get();
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
        clientRepository.save(old);
    }

    public Optional<Client> getById(Long id){
        return clientRepository.findById(id);
    }

    public Optional<Client> getByUsername(String username) {
        return clientRepository.getByUsername(username);
    }

    public List<Client> getByUsernameIgnoringCase(String username) {
        return clientRepository.getByUsernameContainingIgnoreCase(username);
    }

    public Optional<Client> getByEmail(String email){
        return clientRepository.getByEmail(email);
    }
}