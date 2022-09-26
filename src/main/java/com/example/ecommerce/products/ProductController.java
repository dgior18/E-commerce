package com.example.ecommerce.products;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(
                new Product(request.getProductName(),
                        request.getQuantity(),
                        request.getPrice()), request.getUserEmail());
    }

    @PatchMapping("/edit/product/name")
    public String editProductName(@RequestParam("productName") String productName,
                                  @RequestParam("userEmail") String userEmail,
                                  @RequestParam("newProductName") String newProductName) {
        return productService.editProductName(productName, userEmail, newProductName);
    }

    @PatchMapping("/edit/product/price")
    public String editProductprice(@RequestParam("productName") String productName,
                                   @RequestParam("userEmail") String userEmail,
                                   @RequestParam("newPrice") Long newPrice) {
        return productService.editProductPrice(productName, userEmail, newPrice);
    }

    @PatchMapping("/edit/product/quantity")
    public String editProductQuantity(@RequestParam("productName") String productName,
                                      @RequestParam("userEmail") String userEmail,
                                      @RequestParam("newPrice") Long newQuantity) {
        return productService.editProductQuantity(productName, userEmail, newQuantity);
    }

}
