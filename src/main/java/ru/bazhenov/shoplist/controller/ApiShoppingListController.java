package ru.bazhenov.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bazhenov.shoplist.controller.request.NewShoppingListItem;
import ru.bazhenov.shoplist.controller.response.UserShoppingListItem;
import ru.bazhenov.shoplist.persist.entity.ShoppingListItem;
import ru.bazhenov.shoplist.persist.ShoppingListRepository;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.persist.UserRepository;

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
    public List<UserShoppingListItem> getUserItems(Principal principal) {
        List<ShoppingListItem> shoppingListItems = shoppingListRepository.findByUserUsername(principal.getName());
        List<UserShoppingListItem> userShoppingListItems = new ArrayList<>(shoppingListItems.size());
        shoppingListItems.forEach(
                shoppingListItem -> userShoppingListItems.add(
                        new UserShoppingListItem(
                                shoppingListItem.getId(),
                                shoppingListItem.getName()
                        )
                ));
        return userShoppingListItems;
    }

    @PostMapping
    public UserShoppingListItem addNewItem(
            @RequestBody NewShoppingListItem newItem,
            Principal principal
    ) {
        if (newItem.getName().isEmpty()) {
            return null; // TODO: throw exception
        }

        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            ShoppingListItem item = new ShoppingListItem();
            item.setName(newItem.getName());
            item.setUser(foundUser);
            ShoppingListItem savedItem = shoppingListRepository.save(item);
            return new UserShoppingListItem(savedItem.getId(), savedItem.getName());
        }
        return null; // TODO: throw exception
    }

    @DeleteMapping("/{id}")
    public Long deleteItem(@PathVariable("id") Long id) {
        shoppingListRepository.deleteById(id);
        return id;
    }
}
