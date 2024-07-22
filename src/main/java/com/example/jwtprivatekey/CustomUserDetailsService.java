package com.example.jwtprivatekey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService {

    // Inject your user repository or any data access layer here
    // For simplicity, using in-memory data
    private final List<User> store = new ArrayList<>();

    public CustomUserDetailsService() {
        store.add(new User(UUID.randomUUID().toString(), "Bhushan", "bhushan@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Ramesh", "ramesh@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Suresh", "suresh@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Paresh", "paresh@gmail.com"));

    }

    public List<User> getUsers(){
        return this.store;
    }
}

