package com.ForGamers.Model.User;

import com.ForGamers.Model.Product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashSet;


@Entity
@DiscriminatorValue("CLIENT")
@Getter
@Schema(description = "Clase que representa a los clientes.")
public class Client extends User {
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    LinkedHashSet<Product> cart;

    public Client(@Valid String name, @Valid String lastname, @Valid String email, @Valid String phone, @Valid String username, @Valid String password) {
        super(name, lastname, email, phone, username, password);
        this.cart = new LinkedHashSet<>();
    }
}