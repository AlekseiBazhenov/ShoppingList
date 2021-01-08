package ru.bazhenov.shoplist.controller.response;

public class UserShoppingListItem {

    private long id;

    private String name;

    public UserShoppingListItem(long id, String name) {
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
