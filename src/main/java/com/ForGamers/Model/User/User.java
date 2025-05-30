package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    protected Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50)
    @Setter
    @Schema(example = "pepito")
    protected String name;

    @NotBlank(message = "El apellido no puede estra vacio.")
    @Size(max = 50)
    @Setter
    @Schema(example = "Gomez")
    protected String lastname;

    @Column(unique = true)
    @Size(max = 50)
    @Setter
    @Email(message = "El email no es valido.")
    @Schema(example = "example@gmail.com")
    protected String email;

    //Numeros Nacionales de Argentina
    @Pattern(regexp = "^[0-9]{10}$", message = "Numero invalido.")
    @Setter
    @Schema(example = "2235203475")
    protected String phone;

    @Column(unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacio.")
    @Size(max = 20)
    @Setter
    @Schema(example = "rivato")
    protected String username;

    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
    //@Size(max = 20)
    @NotBlank(message = "La clave no puede estar vacia.")
    @Setter
    @Schema(example = "rivato")
    protected String password;

    @NotNull(message = "Campo Obligatorio")
    @Enumerated(EnumType.STRING)
    @Schema(example = "ADMIN")
    protected Role role;

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