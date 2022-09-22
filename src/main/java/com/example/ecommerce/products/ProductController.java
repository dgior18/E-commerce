package com.example.ecommerce.products;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/addProduct")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public String addProduct(@RequestBody ProductRequest request){
        return productService.addProduct(
                new Product(request.getProductName(),
                        request.getQuantity(),
                        request.getPrice()), request.getUserEmail());
    }

}
