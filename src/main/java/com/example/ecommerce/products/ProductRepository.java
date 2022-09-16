package com.example.ecommerce.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long id);

    Optional<Product> findProductByProductName(String name);

}
