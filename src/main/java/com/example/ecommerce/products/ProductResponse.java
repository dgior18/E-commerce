package com.example.ecommerce.products;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductResponse {
    private String productName;
    private Long quantity;
    private Long price;

    public static ProductResponse response (Product product){
        return new ProductResponse(product.getProductName(), product.getQuantity(), product.getPrice());
    }
}
