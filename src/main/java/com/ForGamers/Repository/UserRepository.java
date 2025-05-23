package com.ForGamers.Repository;

import com.ForGamers.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {}