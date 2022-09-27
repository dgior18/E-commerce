package com.example.ecommerce.controllers;

import com.example.ecommerce.homepage.HomeService;
import com.example.ecommerce.products.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v*/homepage/products")
@AllArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping
    public List<ProductResponse> getProducts(){
        return homeService.getProducts();
    }
}
