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
    public Manager(@Valid ManagerDTO dto) {
        super(dto);
        this.role = Role.MANAGER;
    }
}