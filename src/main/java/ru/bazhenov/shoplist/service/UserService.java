package ru.bazhenov.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bazhenov.shoplist.controller.request.NewUser;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.persist.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(NewUser representation) {
        User user = new User();
        user.setUsername(representation.getUsername());
        user.setPassword(passwordEncoder.encode(representation.getPassword()));
        return repository.save(user);
    }

    public Optional<User> getUser(String name) {
        return repository.findByUsername(name);
    }
}
