package com.example.ecommerce.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository
        extends JpaRepository<Product, Long> {


    Optional<Product> findProductByProductName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Product  p " +
            "SET p.quantity = :quantity WHERE p.id = :id")
    int updateQuantity(@Param(value = "quantity") Long quantity, @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Product  p " +
            "SET p.price = :price WHERE p.id = :id")
    int updatePrice(@Param(value = "price") Long price, @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Product  p " +
            "SET p.productName = :productName WHERE p.id = :id")
    int updateProductName(@Param(value = "productName") String productName, @Param(value = "id") Long id);

}
