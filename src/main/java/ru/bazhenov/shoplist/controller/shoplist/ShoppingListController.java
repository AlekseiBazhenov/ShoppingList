package ru.bazhenov.shoplist.controller.shoplist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bazhenov.shoplist.controller.common.ValidationUtils;
import ru.bazhenov.shoplist.controller.common.exception.IncorrectParamsException;
import ru.bazhenov.shoplist.controller.common.exception.RecordNotFoundException;
import ru.bazhenov.shoplist.controller.common.response.ApiResponse;
import ru.bazhenov.shoplist.controller.shoplist.request.NewShoppingList;
import ru.bazhenov.shoplist.controller.shoplist.request.UpdatedShoppingList;
import ru.bazhenov.shoplist.response.*;
import ru.bazhenov.shoplist.service.ProductsService;
import ru.bazhenov.shoplist.service.ShoppingListService;
import ru.bazhenov.shoplist.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/shopping-lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ProductsService productsService;
    private final UserService userService;

    @Autowired
    public ShoppingListController(
            ShoppingListService shoppingListService,
            ProductsService productsService,
            UserService userService
    ) {
        this.shoppingListService = shoppingListService;
        this.productsService = productsService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ShoppingListsData>> getShoppingLists(Principal principal) {
        Optional<UserData> optionalUser = userService.getUser(principal.getName());
        if (optionalUser.isPresent()) {
            ShoppingListsData responseShoppingLists = shoppingListService.get(optionalUser.get().getName());
            return ResponseEntity.ok(new ApiResponse<>(responseShoppingLists, null));
        } else {
            throw new RecordNotFoundException("user not found");
        }
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<ProductsData>> getShoppingListProducts(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Optional<ShoppingListData> shoppingList = shoppingListService.find(id);
        if (shoppingList.isPresent()) {
            ProductsData responseShoppingLists = productsService.getProducts(id);
            return ResponseEntity.ok(new ApiResponse<>(responseShoppingLists, null));
        } else {
            throw new RecordNotFoundException("invalid shopping list id : " + id);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ShoppingListData>> addShoppingList(
            @RequestBody @Valid NewShoppingList body,
            BindingResult validation,
            Principal principal
    ) {
        if (validation.hasErrors()) {
            throw new IncorrectParamsException(ValidationUtils.getValidationErrors(validation));
        }

        Optional<UserData> optionalUser = userService.getUser(principal.getName());
        if (optionalUser.isPresent()) {
            ShoppingListData response = shoppingListService.add(optionalUser.get().getName(), body.getName());
            return ResponseEntity.ok(new ApiResponse<>(response, null));
        } else {
            throw new RecordNotFoundException("user not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShoppingListData>> updateShoppingList(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdatedShoppingList body,
            BindingResult validation,
            Principal principal
    ) {
        if (validation.hasErrors()) {
            throw new IncorrectParamsException(ValidationUtils.getValidationErrors(validation));
        }

        Optional<ShoppingListData> shoppingList = shoppingListService.find(id);
        if (shoppingList.isPresent()) {
            ShoppingListData foundShoppingList = shoppingList.get();
            ShoppingListData savedProduct = shoppingListService.save(foundShoppingList.getId(), body.getName());
            return ResponseEntity.ok(new ApiResponse<>(savedProduct, null));
        } else {
            throw new RecordNotFoundException("invalid shopping list id : " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SuccessResultData>> deleteShoppingList(@PathVariable("id") Long id) {
        if (shoppingListService.isExist(id)) {
            shoppingListService.delete(id);
            SuccessResultData responseData = new SuccessResultData(true);
            return ResponseEntity.ok(new ApiResponse<>(responseData, null));
        } else {
            throw new RecordNotFoundException("invalid shopping list id : " + id);
        }
    }
}
