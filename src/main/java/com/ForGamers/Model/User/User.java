package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@SuperBuilder
@Schema(description = "Clase que representa a los usuarios del sistema.")
public class User {
    @Id
    @GeneratedValue
    @Schema(example = "1")
    protected Long id;

    @Setter
    @Schema(example = "pepito")
    protected String name;

    @Setter
    @Schema(example = "Gomez")
    protected String lastname;

    @Setter
    @Schema(example = "example@gmail.com")
    protected String email;

    //Numeros Nacionales de Argentina
    @Setter
    @Schema(example = "2235203475")
    protected String phone;

    @Setter
    @Schema(example = "rivato")
    protected String username;

    @Setter
    @Schema(example = "rivato")
    protected String password;

    @Enumerated(EnumType.STRING)
    @Schema(example = "ADMIN")
    protected Role role;

    public User(@Valid UserDTO dto) {
        this.name = dto.getName();
        this.lastname = dto.getLastname();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.role = dto.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}