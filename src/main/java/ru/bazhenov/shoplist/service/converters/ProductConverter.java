package ru.bazhenov.shoplist.service.converters;

import org.springframework.stereotype.Component;
import ru.bazhenov.shoplist.persist.entity.Product;
import ru.bazhenov.shoplist.response.ProductData;

@Component
public class ProductConverter {

    public ProductData toResponse(Product product) {
        return new ProductData(product.getId(), product.getName(), product.isBought());
    }

}
