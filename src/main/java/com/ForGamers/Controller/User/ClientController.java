package com.ForGamers.Controller.User;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Model.User.ClientDTO;
import com.ForGamers.Model.User.Enum.Role;
import com.ForGamers.Model.User.User;
import com.ForGamers.Service.User.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    //GET
    //Listado
    @Operation(summary = "Obtener listado de clientes.", description = "Devuelve una lista de todos los clientes.")
    @GetMapping("/all")
    public List<Client> listClients() {
        return services.list();
    }

    //Obtener por id
    @Operation(summary = "Obtener un cliente por id.")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Client> client = services.getById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Obtener por nombre
    @Operation(summary = "Obtener un cliente por nombre.")
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUserame(@PathVariable String username){
        Optional<Client> client = services.getByUsername(username);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @Operation(summary = "Agregar un cliente.", description = "No incluir id al agregar un cliente.")
    @PostMapping
    public ResponseEntity<?>  addClient(@RequestBody @Valid ClientDTO dto) {
        try {
            Client client = new Client(dto);
            client.setRole(Role.CLIENT);
            services.add(client);
            return ResponseEntity.ok(client);
        }catch (ExistentEmailException | ExistentUsernameException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    //DELETE
    @Operation(summary = "Eliminar un cliente por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteClient(@RequestParam Long id){
        return services.delete(id);
    }

    //PUT
    @Operation(summary = "Modificar un cliente.")
    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Client updatedUser) {
        try {
            services.modify(updatedUser.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Peticiones del usuario de la sesión
    //GET
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return services.getByUsername(userDetails.getUsername()).get();
    }

    //PUT todavia no sé si funciona
    @PutMapping("/me/update")
    public ResponseEntity<?> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody Client updatedUser) {
        try {
            Client currentClient = services.getByUsername(userDetails.getUsername()).get();
            services.modify(currentClient.getId(), updatedUser);
            return ResponseEntity.ok(updatedUser);
        }catch (ExistentEmailException | ExistentUsernameException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}