package ru.bazhenov.shoplist.controller.response;

public class UserRegistrationResponse {

    private long id;

    private String name;

    public UserRegistrationResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
