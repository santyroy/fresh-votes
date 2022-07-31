package com.freshvotes.service;

import com.freshvotes.domain.User;
import com.freshvotes.repository.UserRepository;
import com.freshvotes.security.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setAuthority("ROLE_USER");

        user.getAuthorities().add(authority);
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
