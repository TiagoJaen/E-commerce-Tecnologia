package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
@Schema(description = "Clase que representa a los admins.")
public class Admin extends User {
    public Admin(@Valid AdminDTO dto) {
        super(dto);
        this.role = Role.ADMIN;
    }
}