package ru.bazhenov.shoplist.response;

import java.util.List;

public class ShoppingListData {
    private final long id;
    private final String name;
    private final List<ProductData> products;

    public ShoppingListData(long id, String name, List<ProductData> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductData> getProducts() {
        return products;
    }
}
