package ru.bazhenov.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bazhenov.shoplist.persist.ShoppingListRepository;
import ru.bazhenov.shoplist.persist.UserRepository;
import ru.bazhenov.shoplist.persist.entity.ShoppingList;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.response.ShoppingListData;
import ru.bazhenov.shoplist.response.ShoppingListsData;
import ru.bazhenov.shoplist.service.converters.ShoppingListConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    private final UserRepository userRepository;

    private final ShoppingListConverter shoppingListConverter;

    @Autowired
    public ShoppingListService(
            ShoppingListRepository shoppingListRepository,
            UserRepository userRepository,
            ShoppingListConverter shoppingListConverter
    ) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
        this.shoppingListConverter = shoppingListConverter;
    }

    public ShoppingListsData get(String userName) {
        List<ShoppingList> shoppingLists = shoppingListRepository.findByUserUsername(userName);
        List<ShoppingListData> responseShoppingLists;
        if (shoppingLists.isEmpty()) {
            ShoppingList createdShoppingList = createShoppingList(userName, null);
            ShoppingListData response = shoppingListConverter.toResponse(createdShoppingList);
            responseShoppingLists = Collections.singletonList(response);
        } else {
            responseShoppingLists = shoppingLists
                    .stream()
                    .map(shoppingListConverter::toResponse)
                    .collect(Collectors.toList());
        }
        return new ShoppingListsData(responseShoppingLists);
    }

    public ShoppingListData add(String userName, String shoppingListName) {
        ShoppingList createdShoppingList = createShoppingList(userName, shoppingListName);
        return shoppingListConverter.toResponse(createdShoppingList);
    }

    public Optional<ShoppingListData> find(Long id) {
        return shoppingListRepository.findById(id)
                .map(shoppingListConverter::toResponse);
    }

    public ShoppingListData save(long id, String shoppingListName) {
        //noinspection OptionalGetWithoutIsPresent shopping list checked in controller
        ShoppingList foundShoppingList = shoppingListRepository.findById(id).get();
        foundShoppingList.setName(shoppingListName);
        ShoppingList saved = shoppingListRepository.save(foundShoppingList);
        return shoppingListConverter.toResponse(saved);
    }

    public boolean isExist(Long id) {
        return shoppingListRepository.existsById(id);
    }

    public void delete(Long id) {
        shoppingListRepository.deleteById(id);
    }

    private ShoppingList createShoppingList(String userName, String shoppingListName) {
        ShoppingList defaultShoppingList = new ShoppingList();
        defaultShoppingList.setName(shoppingListName == null ? "Shopping List #1" : shoppingListName);
        //noinspection OptionalGetWithoutIsPresent user checked in controller
        User user = userRepository.findByUsername(userName).get();
        defaultShoppingList.setUser(user);
        return shoppingListRepository.save(defaultShoppingList);
    }

}
