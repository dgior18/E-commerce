package com.example.ecommerce.homepage;

import com.example.ecommerce.products.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/homepage/products")
@AllArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping
    public List<Product> getProducts(){
        return homeService.getProducts();
    }
}
