package com.ForGamers.Repository;

import com.ForGamers.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Long> {}