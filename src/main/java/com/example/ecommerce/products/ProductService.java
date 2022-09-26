package com.example.ecommerce.products;

import com.example.ecommerce.appuser.AppUser;
import com.example.ecommerce.appuser.AppUserRepository;
import com.example.ecommerce.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductService {

    private final static String PRODUCT_NOT_FOUND_MSG =
            "product with name %s not found";

    private final ProductRepository productRepository;
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public static int broughtProductsCount = 0;
    public static int broughtProductsSum = 0;
    public static int incomeFromTax = 0;
    public static int addedProductsCount = 0;

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


        if (!appUserService.loadUserByUsername(userEmail).isEnabled()) {
            throw new IllegalStateException("Can't add product. You should verify your account.");
        }

        AppUser user = appUserRepository.findByEmail(userEmail).get();
        product.setUser(user);

        productRepository.save(product);

        addedProductsCount++;

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

        productRepository.updateQuantity(product.getQuantity(), product.getId());

        AppUser user = product.getUser();

        var moneyToPay = product.getPrice() * quantity;

        double newCashAmount = user.getCashAmount() + moneyToPay * 0.9;
        user.setCashAmount(newCashAmount);
        appUserRepository.updateCash(newCashAmount, user.getEmail());

        broughtProductsCount++;
        broughtProductsSum += moneyToPay;
        incomeFromTax += moneyToPay * 0.1;
    }

    public Product verifyProduct(String productName, String userEmail) {

        AppUser user = appUserRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalStateException("user not found"));

        Optional<Product> productList = user.getUsersProducts().stream()
                .filter(product -> product.getProductName()
                        .equals(productName)).findFirst();

        if (productList.isEmpty()) {
            throw new IllegalStateException("product not found");
        }
        return productList.get();
    }

    public String editProductName(String productName, String userEmail, String newProductName) {
        var product = verifyProduct(productName, userEmail);
        product.setProductName(newProductName);
        productRepository.updateProductName(productName, product.getId());
        return "Edited";
    }

    public String editProductPrice(String productName, String userEmail, Long price) {
        var product = verifyProduct(productName, userEmail);
        product.setPrice(price);
        productRepository.updatePrice(price, product.getId());
        return "Edited";
    }

    public String editProductQuantity(String productName, String userEmail, Long quantity) {
        var product = verifyProduct(productName, userEmail);
        product.setQuantity(quantity);
        productRepository.updateQuantity(quantity, product.getId());
        return "Edited";
    }

}
