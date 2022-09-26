package com.example.ecommerce.homepage;

import com.example.ecommerce.products.Product;
import com.example.ecommerce.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeService {

    private final ProductRepository productRepository;


    public static int visitsForToday = 0;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

}
