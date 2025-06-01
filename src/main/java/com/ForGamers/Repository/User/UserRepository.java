package com.ForGamers.Repository.User;

import com.ForGamers.Model.User.User;
import com.ForGamers.Model.User.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    @Query("SELECT u FROM #{#entityName} u WHERE u.username = :username")
    Optional<T> getByUsername(String username);
}