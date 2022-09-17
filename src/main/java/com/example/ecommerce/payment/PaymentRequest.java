package com.example.ecommerce.payment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PaymentRequest {
    private final int cardNumber;
    private final int dateExpires;
    private final int personalCode;
    private final String productName;
    private final int quantity;
}
