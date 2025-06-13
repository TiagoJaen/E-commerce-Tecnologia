package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Schema(description = "Clase que representa a los admins.")
public class Admin extends User {
    public Admin(Long id, String name, String lastname, String email, String phone, String username, String password) {
        super(id, name, lastname, email, phone, username, password);
        this.role = Role.ADMIN;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}