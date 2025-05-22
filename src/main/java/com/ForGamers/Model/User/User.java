package com.ForGamers.Model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Clase que representa a los usuarios del sistema.")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50)
    @Setter
    @Schema(example = "pepito")
    private String name;

    @NotBlank(message = "El apellido no puede estra vacio.")
    @Size(max = 50)
    @Setter
    @Schema(example = "Gomez")
    private String lastname;

    @Size(max = 50)
    @Setter
    @Email(message = "El email no es valido.")
    @Schema(example = "example@gmail.com")
    private String email;

    //Numeros Nacionales de Argentina
    @Pattern(regexp = "^[0-9]{10}$", message = "Numero invalido.")
    @Setter
    @Schema(example = "2235203475")
    private String phone;

    @NotBlank(message = "El nombre de usuario no puede estar vacio.")
    @Size(max = 20)
    @Setter
    @Schema(example = "rivato")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
    @NotBlank(message = "La clave no puede estar vacia.")
    @Size(max = 20)
    @Setter
    @Schema(example = "rivato")
    private String password;

    public User(@Valid String name, @Valid String lastname, @Valid String email, @Valid String phone, @Valid String username, @Valid String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
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