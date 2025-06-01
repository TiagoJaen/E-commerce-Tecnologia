package com.ForGamers.Model.User;

import com.ForGamers.Model.User.Enum.Role;
import jakarta.validation.Valid;

public class AdminDTO extends UserDTO{
    public AdminDTO(String name, String lastname, String email, String phone, String username, String password) {
        super(name, lastname, email, phone, username, password);
        this.role = Role.ADMIN;
    }
}