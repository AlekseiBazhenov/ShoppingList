package ru.bazhenov.shoplist.controller;

public class NewShoppingListItem {

    private String name;

    public NewShoppingListItem() {}

    public NewShoppingListItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
