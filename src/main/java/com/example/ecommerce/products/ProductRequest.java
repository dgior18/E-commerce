package com.example.ecommerce.products;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductRequest {
    private final String productName;
    private final Long quantity;
    private final Long price;
    private final String userEmail;
}
