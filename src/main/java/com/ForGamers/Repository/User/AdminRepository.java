package com.ForGamers.Repository.User;

import com.ForGamers.Model.User.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT u FROM #{#entityName} u WHERE u.username = :username")
    Optional<Admin> getByUsername(@Param("username") String username);

    @Query("SELECT u FROM #{#entityName} u WHERE u.email = :email")
    Optional<Admin> getByEmail(@Param("email")String email);
}