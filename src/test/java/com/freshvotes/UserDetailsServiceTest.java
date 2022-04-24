package com.freshvotes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceTest {

    @Test
    public void generateEncryptedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123";
        String encodePassword = encoder.encode(rawPassword);
        System.out.println(encodePassword);
        Assertions.assertNotEquals(rawPassword, encodePassword);
    }
}
