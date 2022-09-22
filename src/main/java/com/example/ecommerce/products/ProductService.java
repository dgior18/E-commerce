package com.example.ecommerce.products;

import com.example.ecommerce.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductService {

    private final static String PRODUCT_NOT_FOUND_MSG =
            "product with name %s not found";

    private final ProductRepository productRepository;
    private final AppUserService appUserService;

    public Product loadProductByName(String productName)
            throws UsernameNotFoundException {
        return productRepository.findProductByProductName(productName)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(PRODUCT_NOT_FOUND_MSG, productName)));
    }

    public String addProduct(Product product, String userEmail) {

        boolean productExist = productRepository
                .findProductByProductName(product.getProductName())
                .isPresent();

        if (productExist) {
            throw new IllegalStateException("product already added");
        }

        if (!appUserService.loadUserByUsername(userEmail).isEnabled()){
            throw new IllegalStateException("Can't add product. You should verify your account.");
        }

        productRepository.save(product);

        return "Product added";
    }

    public void buyProduct(String productName, Long quantity) {


        Product product = productRepository
                .findProductByProductName(productName)
                .get();

        Long currQuantity = product.getQuantity();

        if (currQuantity < quantity) {
            throw new IllegalStateException("Not enough in store");
        }

        product.setQuantity(currQuantity - quantity);

        productRepository.updateProduct(product.getQuantity(), productName);
    }

}
