package ru.bazhenov.shoplist.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazhenov.shoplist.persist.entity.ShoppingList;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    List<ShoppingList> findByUserUsername(String username);

}
