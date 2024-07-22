package com.example.jwtprivatekey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/test")
    public ResponseEntity<List<User>> testToken(@RequestParam String name) {
        System.out.println("getting users");
        List<User> users = customUserDetailsService.getUsers();
        return ResponseEntity.ok(users);
    }
}

