package ru.bazhenov.shoplist.response;

public class UserData {

    private final long id;

    private final String name;

    public UserData(long id, String name) {
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
