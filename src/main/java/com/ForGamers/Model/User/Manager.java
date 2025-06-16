package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
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
@Schema(description = "Clase que representa a los gestores.")
public class Manager extends User {
    public Manager(ManagerDTO dto) {
        super(dto);
        this.role = Role.MANAGER;
    }

    public Manager(User user){
        this.id = (user.getId());
        this.name = (user.getName());
        this.lastname = (user.getLastname());
        this.username = (user.getUsername());
        this.phone = (user.getPhone());
        this.password = (user.getPassword());
        this.email = (user.getEmail());
        this.role = Role.MANAGER;
    }
}