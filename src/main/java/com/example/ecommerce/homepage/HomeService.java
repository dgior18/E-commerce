package com.example.ecommerce.homepage;

import com.example.ecommerce.products.ProductRepository;
import com.example.ecommerce.products.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomeService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream().map(product -> ProductResponse.response(product)).collect(Collectors.toList());
    }

}
