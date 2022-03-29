package ru.bazhenov.shoplist.response;

public class ShoppingListData {
    private final long id;
    private final String name;
    private final int productsCount;

    public ShoppingListData(long id, String name, int productsCount) {
        this.id = id;
        this.name = name;
        this.productsCount = productsCount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProductsCount() {
        return productsCount;
    }
}