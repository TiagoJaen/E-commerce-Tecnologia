package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;

@Entity
@DiscriminatorValue("MANAGER")
@Schema(description = "Clase que representa a los gestores.")
public class Manager extends User {
    public Manager(@Valid String name, @Valid String lastname, @Valid String email, @Valid String phone, @Valid String username, @Valid String password) {
        super(name, lastname, email, phone, username, password);
        this.role = Role.MANAGER;
    }
}