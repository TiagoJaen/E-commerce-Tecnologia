package com.ForGamers.Repository.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("SELECT u FROM #{#entityName} u WHERE u.username = :username")
    Optional<Manager> getByUsername(@Param("username") String username);

    @Query("SELECT u FROM #{#entityName} u WHERE u.email = :email")
    Optional<Manager> getByEmail(@Param("email")String email);

    List<Manager> getByUsernameContainingIgnoreCase(String username);
}
