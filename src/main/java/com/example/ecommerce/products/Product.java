package com.example.ecommerce.products;

import com.example.ecommerce.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {

    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    @Column(unique = true)
    private String productName;
    private Long quantity;
    private Long price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Product(String productName,
                   Long quantity,
                   Long price){
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
