package com.example.ecommerce.payment;

import com.example.ecommerce.products.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

    private final ProductService productService;

    public String pay(PaymentRequest request) {

        if (!checkValidation(request)) {
            log.error("Wrong credentials. Can't pay.");
            return "Wrong credentials. Can't pay.";
        }

        productService.buyProduct(request.getProductName(), request.getQuantity());

        return "successful payment";

    }

    private boolean checkValidation(PaymentRequest request) {

        return String.valueOf(request.getPersonalCode()).length() == 3 &&
                String.valueOf(request.getCardNumber()).length() == 16;
    }


}
