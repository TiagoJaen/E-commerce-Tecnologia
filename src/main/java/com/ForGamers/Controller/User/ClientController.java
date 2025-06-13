package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.ClientDTO;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Service.User.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@Getter
@Tag(name = "clients", description = "Operaciones relacionadas con los clientes.")
public class ClientController {
    private ClientService services;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener listado de clientes.", description = "Devuelve una lista de todos los clientes.")
    @GetMapping
    public List<Client> listClients() {
        return services.list();
    }

    @Operation(summary = "Agregar un cliente.", description = "No incluir id al agregar un cliente.")
    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody ClientDTO dto) {
        try {
            Client client = new Client(dto);
            client.setRole(Role.CLIENT);
            Client saved = services.add(client);
            return ResponseEntity.ok(saved);
        }catch (ExistentEmailException | ExistentUsernameException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Eliminar un cliente por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteClient(@RequestParam Long id){
        return services.delete(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener un cliente por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id") Long id){
        Optional<Client> client = services.getById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener un cliente por nombre.")
    @GetMapping(value = "/username", params = "username")
    public ResponseEntity<?> getByUserame(@RequestParam(name = "username") String username){
        Optional<Client> client = services.getByUsername(username);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Editar un cliente.")
    @PutMapping
    public ResponseEntity<Void> modifyClient(@RequestBody Client updatedClient) {
        return services.modify(updatedClient.getId(), updatedClient);
    }
}