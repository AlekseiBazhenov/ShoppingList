package ru.bazhenov.shoplist.response;

import java.util.List;

public class ProductsData {
    private final List<ProductData> products;

    public ProductsData(List<ProductData> products) {
        this.products = products;
    }

    public List<ProductData> getProducts() {
        return products;
    }
}
