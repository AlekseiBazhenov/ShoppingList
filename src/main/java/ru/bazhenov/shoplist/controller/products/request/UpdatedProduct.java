package ru.bazhenov.shoplist.controller.products.request;

import javax.validation.constraints.Positive;

public class UpdatedProduct {

    @Positive(message = "shopping list ID is required")
    private long shoppingListId;

    private boolean bought;

    public long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
