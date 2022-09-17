package com.example.ecommerce.payment;

import com.example.ecommerce.products.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final ProductService productService;

    public String pay(PaymentRequest request) {

        if (!checkValidation(request)){
            return "Wrong credentials. Can't pay.";
        }

        productService.buyProduct(request.getProductName(), request.getQuantity());

        return "successful payment";

    }

    private boolean checkValidation(PaymentRequest request) {

        if (String.valueOf(request.getCardNumber()).length() != 16) {
            return false;
        }

        if (String.valueOf(request.getPersonalCode()).length()!=3){
            return false;
        }

        return true;
    }


}
