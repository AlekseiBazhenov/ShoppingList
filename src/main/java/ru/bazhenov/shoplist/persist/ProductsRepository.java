package ru.bazhenov.shoplist.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazhenov.shoplist.persist.entity.Product;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    List<Product> findByShoppingListId(long id);

}
