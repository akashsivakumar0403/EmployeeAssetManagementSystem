package com.eams.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePassword {
    public static void main(String[] args) {
        String rawPassword = "manager123";  // 👈 Replace this with any password
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}
