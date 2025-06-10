package com.ForGamers.Service.User;

import com.ForGamers.Model.User.User;
import com.ForGamers.Repository.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeneralUserService extends UserService<User, UserRepository<User>> implements UserDetailsService {

    public GeneralUserService(UserRepository<User> repository,
                              UserLookupService userLookupService) {
        super(repository, userLookupService);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userLookupService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRole().toString())
                .build();
    }
}
