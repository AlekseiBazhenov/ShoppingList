package ru.bazhenov.shoplist.response;

public class ProductData {

    private final long id;
    private final String name;
    private final boolean bought;

    public ProductData(long id, String name, boolean bought) {
        this.id = id;
        this.name = name;
        this.bought = bought;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBought() {
        return bought;
    }
}
