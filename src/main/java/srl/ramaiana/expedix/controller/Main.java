package srl.ramaiana.expedix.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password1 = encoder.encode("password123");
        String password2 = encoder.encode("password456");
        String password3 = encoder.encode("password789");
        System.out.println(password1);
        System.out.println(password2);
        System.out.println(password3);
    }
}
