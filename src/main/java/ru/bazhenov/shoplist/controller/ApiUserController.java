package ru.bazhenov.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bazhenov.shoplist.controller.response.UserRegistrationResponse;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.controller.request.NewUser;
import ru.bazhenov.shoplist.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    private final UserService userService;

    @Autowired
    public ApiUserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    // TODO: check auth when login

    // TODO: exceptions with http code.
    //  https://www.baeldung.com/exception-handling-for-rest-with-spring
    //  https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

    @PostMapping("/register")
    public UserRegistrationResponse registerNewUser(
            @RequestBody @Valid NewUser newUser,
            BindingResult validation
    ) {
        if (validation.hasErrors()) {
            throw new IllegalArgumentException(validation.getFieldErrors().toString());
        }

        if (!newUser.getPassword().equals(newUser.getRepeatPassword())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        User createdUser = userService.create(newUser);
        return new UserRegistrationResponse(createdUser.getId(), createdUser.getUsername());
    }
}
