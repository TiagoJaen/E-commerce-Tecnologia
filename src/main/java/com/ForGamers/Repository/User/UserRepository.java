package com.ForGamers.Repository.User;

import com.ForGamers.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<T> getByUsername(@Param("username") String username);
}