package ru.bazhenov.shoplist.controller.products.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NewProduct {

    @Positive(message = "shopping list ID is required")
    private long shoppingListId;

    @NotBlank(message = "product name is required")
    private String name;

    public long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
