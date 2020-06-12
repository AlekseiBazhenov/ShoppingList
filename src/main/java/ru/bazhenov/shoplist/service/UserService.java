package ru.bazhenov.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bazhenov.shoplist.persist.User;
import ru.bazhenov.shoplist.persist.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRepresentation representation) {
        User user = new User();
        user.setUsername(representation.getUsername());
        user.setPassword(passwordEncoder.encode(representation.getPassword()));
        repository.save(user);
    }
}