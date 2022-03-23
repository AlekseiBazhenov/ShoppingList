package ru.bazhenov.shoplist.controller.shoplist.request;

import javax.validation.constraints.NotBlank;

public class UpdatedShoppingList {

    @NotBlank(message = "shopping list name is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
