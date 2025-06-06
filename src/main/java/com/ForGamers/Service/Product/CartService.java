package com.ForGamers.Service.Product;

import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Repository.Product.CartRepository;
import com.ForGamers.Repository.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;
    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public ResponseEntity<Void> deleteCart(Long id){
        if (!cartRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Product> getProductsInClientCart(Long id) {
        return cartRepository.findProductsByClientId(id);
    }

    public Cart getById(Long id){
        return cartRepository.getById(id);
    }
}
