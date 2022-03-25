package ru.bazhenov.shoplist.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bazhenov.shoplist.controller.common.ValidationUtils;
import ru.bazhenov.shoplist.controller.common.exception.IncorrectParamsException;
import ru.bazhenov.shoplist.controller.common.exception.RecordAlreadyExistException;
import ru.bazhenov.shoplist.controller.common.exception.RecordNotFoundException;
import ru.bazhenov.shoplist.controller.common.response.ApiResponse;
import ru.bazhenov.shoplist.controller.user.request.NewUser;
import ru.bazhenov.shoplist.response.UserData;
import ru.bazhenov.shoplist.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserData>> checkLogin(
            Principal principal
    ) {
        Optional<UserData> optionalUser = userService.getUser(principal.getName());
        if (!optionalUser.isPresent()) {
            throw new RecordNotFoundException("user not found");
        } else {
            UserData foundUser = optionalUser.get();
            return ResponseEntity.ok(new ApiResponse<>(foundUser, null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserData>> registerNewUser(
            @RequestBody @Valid NewUser newUser,
            BindingResult validation
    ) {
        if (validation.hasErrors()) {
            throw new IncorrectParamsException(ValidationUtils.getValidationErrors(validation));
        }

        if (!newUser.getPassword().equals(newUser.getPasswordConfirmation())) {
            throw new IncorrectParamsException("password mismatch");
        }

        if (userService.getUser(newUser.getUsername()).isPresent()) {
            throw new RecordAlreadyExistException(newUser.getUsername() + " already registered");
        }

        UserData createdUser = userService.create(newUser);
        return ResponseEntity.ok(new ApiResponse<>(createdUser, null));
    }
}
