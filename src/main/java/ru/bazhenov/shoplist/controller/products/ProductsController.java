package ru.bazhenov.shoplist.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bazhenov.shoplist.controller.common.ValidationUtils;
import ru.bazhenov.shoplist.controller.common.exception.IncorrectParamsException;
import ru.bazhenov.shoplist.controller.common.exception.RecordNotFoundException;
import ru.bazhenov.shoplist.controller.common.response.ApiResponse;
import ru.bazhenov.shoplist.controller.products.request.NewProduct;
import ru.bazhenov.shoplist.controller.products.request.UpdatedProduct;
import ru.bazhenov.shoplist.response.*;
import ru.bazhenov.shoplist.service.ProductsService;
import ru.bazhenov.shoplist.service.ShoppingListService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ProductsController {

    private final ProductsService productsService;
    private final ShoppingListService shoppingListService;

    @Autowired
    public ProductsController(
            ProductsService productsService,
            ShoppingListService shoppingListService
    ) {
        this.productsService = productsService;
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductData>> addProduct(
            @RequestBody @Valid NewProduct body,
            BindingResult validation,
            Principal principal
    ) {
        if (validation.hasErrors()) {
            throw new IncorrectParamsException(ValidationUtils.getValidationErrors(validation));
        }

        Optional<ShoppingListData> shoppingList = shoppingListService.find(body.getShoppingListId());
        if (!shoppingList.isPresent()) {
            throw new RecordNotFoundException("shopping list not found");
        }

        long shoppingListId = shoppingList.get().getId();
        ProductData savedProduct = productsService.saveNewProduct(body.getName(), shoppingListId);
        return ResponseEntity.ok(new ApiResponse<>(savedProduct, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductData>> updateProductBought(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdatedProduct body,
            BindingResult validation,
            Principal principal
    ) {
        if (validation.hasErrors()) {
            throw new IncorrectParamsException(ValidationUtils.getValidationErrors(validation));
        }

        Optional<ShoppingListData> shoppingList = shoppingListService.find(body.getShoppingListId());
        if (!shoppingList.isPresent()) {
            throw new RecordNotFoundException("shopping list not found");
        }

        Optional<ProductData> optionalExistingProduct = productsService.find(id);
        if (!optionalExistingProduct.isPresent()) {
            throw new RecordNotFoundException("invalid item id : " + id);
        } else {
            ProductData savedProduct = productsService.updateProduct(body.isBought(), id);
            return ResponseEntity.ok(new ApiResponse<>(savedProduct, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SuccessResultData>> deleteProduct(@PathVariable("id") Long id) {
        if (productsService.isExist(id)) {
            productsService.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(new SuccessResultData(true), null));
        } else {
            throw new RecordNotFoundException("invalid item id : " + id);
        }
    }
}
