package ru.bazhenov.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bazhenov.shoplist.controller.user.request.NewUser;
import ru.bazhenov.shoplist.persist.UserRepository;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.response.UserData;
import ru.bazhenov.shoplist.service.converters.UserConverter;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder, UserConverter userConverter) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }

    public UserData create(NewUser representation) {
        User user = new User();
        user.setUsername(representation.getUsername());
        user.setPassword(passwordEncoder.encode(representation.getPassword()));
        User saved = repository.save(user);
        return userConverter.toResponse(saved);
    }

    public Optional<UserData> getUser(String name) {
        return repository.findByUsername(name)
                .map(userConverter::toResponse);
    }
}
