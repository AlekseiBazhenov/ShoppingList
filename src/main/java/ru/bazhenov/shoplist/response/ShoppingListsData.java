package ru.bazhenov.shoplist.response;

import java.util.List;

public class ShoppingListsData {
    private final List<ShoppingListData> shoppingLists;

    public ShoppingListsData(List<ShoppingListData> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public List<ShoppingListData> getShoppingLists() {
        return shoppingLists;
    }
}
