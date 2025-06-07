package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.ClientDTO;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Service.User.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@Getter
@Tag(name = "clients", description = "Operaciones relacionadas con los clientes.")
public class ClientController {
    private ClientService services;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener listado de clientes.", description = "Devuelve una lista de todos los clientes.")
    @GetMapping
    public List<Client> listClients() {
        return services.list();
    }

    @Operation(summary = "Agregar un cliente.", description = "No incluir id al agregar un cliente.")
    @PostMapping
    public ResponseEntity<?>  addClient(@RequestBody ClientDTO dto) {
        try {
            Client client = new Client(dto);
            client.setRole(Role.CLIENT);
            Client saved = services.add(client);
            return ResponseEntity.ok(saved);
        }catch (ExistentEmailException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }catch (ExistentUsernameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar un cliente por id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        return services.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener un cliente por id.")
    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id){
        return services.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Editar un cliente.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return services.modify(id, updatedClient);
    }
}