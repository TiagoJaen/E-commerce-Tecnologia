package com.ForGamers.Model.Product;

import com.ForGamers.Model.User.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private int cantInCart;

    public CartEntry(Product product, Client client, int cantInCart) {
        this.product = product;
        this.client = client;
        this.cantInCart = cantInCart;
    }
}
