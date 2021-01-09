package ru.bazhenov.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bazhenov.shoplist.controller.request.NewItem;
import ru.bazhenov.shoplist.controller.response.ItemResponse;
import ru.bazhenov.shoplist.persist.entity.ShoppingListItem;
import ru.bazhenov.shoplist.persist.ShoppingListRepository;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.persist.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ApiShoppingListController {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;

    @Autowired
    public ApiShoppingListController(
            ShoppingListRepository shoppingListRepository,
            UserRepository userRepository
    ) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<ItemResponse> getUserItems(Principal principal) {
        List<ShoppingListItem> shoppingListItems = shoppingListRepository.findByUserUsername(principal.getName());
        List<ItemResponse> itemRespons = new ArrayList<>(shoppingListItems.size());
        shoppingListItems.forEach(
                shoppingListItem -> itemRespons.add(
                        new ItemResponse(
                                shoppingListItem.getId(),
                                shoppingListItem.getName()
                        )
                ));
        return itemRespons;
    }

    @PostMapping
    public ItemResponse addNewItem(
            @RequestBody @Valid NewItem newItem,
            BindingResult validation,
            Principal principal
    ) {
        if (validation.hasErrors()) {
            throw new IllegalArgumentException(validation.getFieldErrors().toString());
        }

        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            ShoppingListItem item = new ShoppingListItem();
            item.setName(newItem.getName());
            item.setUser(foundUser);
            ShoppingListItem savedItem = shoppingListRepository.save(item);
            return new ItemResponse(savedItem.getId(), savedItem.getName());
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }

    @DeleteMapping("/{id}")
    public Long deleteItem(@PathVariable("id") Long id) {
        shoppingListRepository.deleteById(id);
        return id;
    }
}
