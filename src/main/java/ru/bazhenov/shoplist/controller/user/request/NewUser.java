package ru.bazhenov.shoplist.controller.user.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewUser {

    @NotBlank(message = "username is required")
    @Email(message = "email is not valid")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "passwordConfirmation is required")
    private String passwordConfirmation;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
