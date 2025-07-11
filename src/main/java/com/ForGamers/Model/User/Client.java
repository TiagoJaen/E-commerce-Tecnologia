package com.ForGamers.Model.User;

import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Schema(description = "Clase que representa a los clientes.")
public class Client extends User {
    public Client(ClientDTO dto) {
        super(dto);
        this.role = Role.CLIENT;
    }
}