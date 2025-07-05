package com.ForGamers.Model.Product;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    // Comentado porque por ahora no va a necesitar id (no tiene tabla propia de SQL)
    //private Long id;

    private Long clientId;
    // Pairs: cantInCart (left), productId (right)
    private List<CartProductPair> contents;

    //private Pair<Integer, Product> pair = new MutablePair<>();

    public Cart(Long clientId, List<CartProductPair> contents) {
        this.clientId = clientId;
        this.contents = contents;
    }

    public Cart(Long clientId) {
        this.clientId = clientId;
        this.contents = new ArrayList<>();
    }
}
