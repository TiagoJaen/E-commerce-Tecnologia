package com.ForGamers.Repository.User;

import com.ForGamers.Model.User.Admin;
import com.ForGamers.Model.User.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends UserRepository<Admin>, JpaRepository<Admin, Long> {
    @Query("SELECT u FROM #{#entityName} u WHERE u.username = :username")
    @Override
    Optional<Admin> getByUsername(String username);

    @Query("SELECT u FROM #{#entityName} u WHERE u.email = :email")
    @Override
    Optional<Admin> getByEmail(String email);
}