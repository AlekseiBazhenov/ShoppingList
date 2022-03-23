package ru.bazhenov.shoplist.service.converters;

import org.springframework.stereotype.Component;
import ru.bazhenov.shoplist.persist.entity.ShoppingList;
import ru.bazhenov.shoplist.response.ProductData;
import ru.bazhenov.shoplist.response.ShoppingListData;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShoppingListConverter {

    public ShoppingListData toResponse(ShoppingList shoppingList) {
        List<ProductData> products = new ArrayList<>(shoppingList.getProducts().size());
        shoppingList.getProducts().forEach(product -> products.add(new ProductData(product.getId(), product.getName(), product.isBought())));
        return new ShoppingListData(shoppingList.getId(), shoppingList.getName(), products);
    }

}
