package com.example.ecommerce.products;

import com.example.ecommerce.appuser.AppUser;
import com.example.ecommerce.appuser.AppUserRepository;
import com.example.ecommerce.appuser.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public static int broughtProductsCount = 0;
    public static int broughtProductsSum = 0;
    public static int incomeFromTax = 0;
    public static int addedProductsCount = 0;

    public String addProduct(Product product, String userEmail) {

        boolean productExist = productRepository
                .findProductByProductName(product.getProductName())
                .isPresent();

        if (productExist) {
            log.error("Product already added.");
            throw new IllegalStateException("Product already added.");
        }


        if (!appUserService.loadUserByUsername(userEmail).isEnabled()) {
            log.error("Can't add product. You should verify your account.");
            throw new IllegalStateException("Can't add product. You should verify your account.");
        }

        boolean check = appUserRepository.findByEmail(userEmail).isPresent();
        if (!check) {
            log.error("User doesn't exist.");
            throw new IllegalStateException("User doesn't exist.");
        }

        AppUser user = appUserRepository.findByEmail(userEmail).get();
        product.setUser(user);

        productRepository.save(product);

        log.info(userEmail + " added Product with name: " + product.getProductName() + ".");

        addedProductsCount++;

        return "Product added";
    }

    public void buyProduct(String productName, Long quantity) {

        boolean check = productRepository.findProductByProductName(productName).isPresent();
        if (!check) {
            log.error("Product doesn't exist.");
            throw new IllegalStateException("Product doesn't exist.");
        }

        Product product = productRepository
                .findProductByProductName(productName)
                .get();

        Long currQuantity = product.getQuantity();

        if (currQuantity < quantity) {
            log.error("Not enough in store.");
            throw new IllegalStateException("Not enough in store.");
        }

        product.setQuantity(currQuantity - quantity);

        productRepository.updateQuantity(product.getQuantity(), product.getId());

        log.info("Updated quantity of product with name: " + productName + ".");

        AppUser user = product.getUser();

        var moneyToPay = product.getPrice() * quantity;

        double newCashAmount = user.getCashAmount() + moneyToPay * 0.9;
        user.setCashAmount(newCashAmount);
        appUserRepository.updateCash(newCashAmount, user.getEmail());

        log.info("Product with name: " + productName + "brought. Price was: " + moneyToPay + ".");

        broughtProductsCount++;
        broughtProductsSum += moneyToPay;
        incomeFromTax += moneyToPay * 0.1;
    }

    public Product verifyProduct(String productName, String userEmail) {

        AppUser user = appUserRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalStateException("User not found."));

        Optional<Product> productList = user.getUsersProducts().stream()
                .filter(product -> product.getProductName()
                        .equals(productName)).findFirst();

        if (productList.isEmpty()) {
            log.error("Product not found.");
            throw new IllegalStateException("Product not found.");
        }
        return productList.get();
    }

    public String editProductName(String productName, String userEmail, String newProductName) {
        var product = verifyProduct(productName, userEmail);
        product.setProductName(newProductName);
        log.info("Edited product name. Old name was " + productName + ", new name is " + newProductName);
        productRepository.updateProductName(productName, product.getId());
        return "Edited";
    }

    public String editProductPrice(String productName, String userEmail, Long price) {
        var product = verifyProduct(productName, userEmail);
        product.setPrice(price);
        log.info("Edited product price. Old price was " + product.getPrice() + ", new price is " + price);
        productRepository.updatePrice(price, product.getId());
        return "Edited";
    }

    public String editProductQuantity(String productName, String userEmail, Long quantity) {
        var product = verifyProduct(productName, userEmail);
        product.setQuantity(quantity);
        log.info("Edited product quantity. Old quantity was " + product.getQuantity() + ", new quantity is " + quantity);
        productRepository.updateQuantity(quantity, product.getId());
        return "Edited";
    }

}
