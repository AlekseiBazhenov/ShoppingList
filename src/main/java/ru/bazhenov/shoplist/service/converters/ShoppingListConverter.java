package ru.bazhenov.shoplist.service.converters;

import org.springframework.stereotype.Component;
import ru.bazhenov.shoplist.persist.entity.ShoppingList;
import ru.bazhenov.shoplist.response.ShoppingListData;

@Component
public class ShoppingListConverter {

    public ShoppingListData toResponse(ShoppingList shoppingList) {
        return new ShoppingListData(shoppingList.getId(), shoppingList.getName(), shoppingList.getProductsCount());
    }

}
