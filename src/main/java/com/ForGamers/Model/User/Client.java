package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Schema(description = "Clase que representa a los clientes.")
public class Client extends User {
    /*@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    LinkedHashSet<Product> cart;*/

    public Client(ClientDTO dto) {
        super(dto);
        this.role = Role.CLIENT;
        // this.cart = new LinkedHashSet<>();
    }
}