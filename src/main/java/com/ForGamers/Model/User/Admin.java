package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Entity
@DiscriminatorValue("ADMIN")
@Schema(description = "Clase que representa a los admins.")
public class Admin extends User {
    public Admin(@Valid String name, @Valid String lastname, @Valid String email, @Valid String phone, @Valid String username, @Valid String password) {
        super(name, lastname, email, phone, username, password);
        this.role = Role.ADMIN;
    }
}