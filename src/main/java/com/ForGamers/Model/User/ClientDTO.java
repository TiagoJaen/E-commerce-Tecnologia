package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;

public class ClientDTO extends UserDTO{
    public ClientDTO(String name, String lastname, String email, String phone, String username, String password) {
        super(name, lastname, email, phone, username, password);
        this.role = Role.CLIENT;
    }
}
