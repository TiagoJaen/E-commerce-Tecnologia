package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;


//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Schema(description = "Clase que representa a los usuarios del sistema.")
public class User {
    @Id
    @GeneratedValue
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    protected Long id;

    // @Setter
    @Schema(example = "pepito")
    protected String name;

    @Schema(example = "Gomez")
    protected String lastname;

    // @Setter
    @Schema(example = "example@gmail.com")
    protected String email;

    //Numeros Nacionales de Argentina
    // @Setter
    @Schema(example = "2235203475")
    protected String phone;

    // @Setter
    @Schema(example = "rivato")
    protected String username;

    // @Setter
    @Schema(example = "1234")
    protected String password;

    // @Setter
    @Enumerated(EnumType.STRING)
    protected Role role;

    public User(Long id, String name, String lastname, String email, String phone, String username, String password) {
        this.id = id;
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