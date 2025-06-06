package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
public abstract class UserDTO {
    protected Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50)
    protected String name;

    @NotBlank(message = "El apellido no puede estra vacio.")
    @Size(max = 50)
    protected String lastname;

    @Column(unique = true)
    @Size(max = 50)
    @Email(message = "El email no es valido.")
    protected String email;

    //Numeros Nacionales de Argentina
    @Pattern(regexp = "^[0-9]{10}$",
            message = "Por favor ingrese un número de teléfono válido..")
    protected String phone;

    @Column(unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacio.")
    @Size(max = 20)
    protected String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,15}$",
             message = "La contraseña debe tener entre 8 y 15 caracteres, al menos una mayuscula, " +
                     "una minuscula y un caracter especial(@$!%*?&.).")
    @NotBlank(message = "La contraseña no puede ser nula.")
    protected String password;

    @Enumerated(EnumType.STRING)
    protected Role role;

    public UserDTO(String name, String lastname, String email, String phone, String username, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public UserDTO(UserDTO dto) {
        this.name = dto.getName();
        this.lastname = dto.getLastname();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }
}