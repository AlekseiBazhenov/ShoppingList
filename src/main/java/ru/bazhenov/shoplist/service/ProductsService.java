package ru.bazhenov.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bazhenov.shoplist.persist.ProductsRepository;
import ru.bazhenov.shoplist.persist.ShoppingListRepository;
import ru.bazhenov.shoplist.persist.entity.Product;
import ru.bazhenov.shoplist.persist.entity.ShoppingList;
import ru.bazhenov.shoplist.response.ProductData;
import ru.bazhenov.shoplist.response.ProductsData;
import ru.bazhenov.shoplist.service.converters.ProductConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ShoppingListRepository shoppingListRepository;

    private final ProductConverter productConverter;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, ShoppingListRepository shoppingListRepository, ProductConverter productConverter) {
        this.productsRepository = productsRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.productConverter = productConverter;
    }

    public ProductData saveNewProduct(String name, long shoppingListId) {
        Product product = new Product();
        product.setName(name);
        //noinspection OptionalGetWithoutIsPresent shopping list checked in controller
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId).get();
        product.setShoppingList(shoppingList);
        Product saved = productsRepository.save(product);
        return productConverter.toResponse(saved);
    }

    public ProductData updateProduct(boolean bought, long productId) {
        //noinspection OptionalGetWithoutIsPresent product checked in controller
        Product product = productsRepository.findById(productId).get();
        product.setBought(bought);
        Product saved = productsRepository.save(product);
        return productConverter.toResponse(saved);
    }

    public Optional<ProductData> find(Long id) {
        return productsRepository.findById(id)
                .map(productConverter::toResponse);
    }

    public boolean isExist(Long id) {
        return productsRepository.existsById(id);
    }

    public void delete(Long id) {
        productsRepository.deleteById(id);
    }

    public ProductsData getProducts(Long id) {
        List<ProductData> data = productsRepository.findByShoppingListId(id)
                .stream()
                .map(productConverter::toResponse)
                .collect(Collectors.toList());
        return new ProductsData(data);
    }
}
