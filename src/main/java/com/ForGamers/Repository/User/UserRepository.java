package com.ForGamers.Repository.User;

import com.ForGamers.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository<T extends User> {
    Optional<T> getByUsername(String username);
    Optional<T> getByEmail(String email);
}