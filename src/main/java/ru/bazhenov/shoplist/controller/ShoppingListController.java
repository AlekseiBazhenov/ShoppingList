package ru.bazhenov.shoplist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bazhenov.shoplist.persist.entity.ShoppingListItem;
import ru.bazhenov.shoplist.persist.ShoppingListRepository;
import ru.bazhenov.shoplist.persist.entity.User;
import ru.bazhenov.shoplist.persist.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ShoppingListController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingListController.class);

    private final ShoppingListRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public ShoppingListController(ShoppingListRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String indexPage(Model model, Principal principal) {
        logger.info("User name: {}", principal.getName());

        model.addAttribute("items", repository.findByUserUsername(principal.getName()));
        model.addAttribute("item", new ShoppingListItem());
        return "index";
    }

    @PostMapping
    public String newShoppingItem(ShoppingListItem item, Principal principal) {
        logger.info("User name: {}", principal.getName());

        if (item.getName().isEmpty()) {
            // TODO: 12.06.2020 show error
            return "redirect:/";
        }

        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            item.setUser(user);
            repository.save(item);
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
