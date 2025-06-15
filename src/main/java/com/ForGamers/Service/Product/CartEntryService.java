package com.ForGamers.Service.Product;

import com.ForGamers.Model.Product.CartEntry;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Repository.Product.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartEntryService {
    private CartRepository cartRepository;
    @Autowired
    public CartEntryService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartEntry addCart(CartEntry cartEntry) {
        return cartRepository.save(cartEntry);
    }

    public ResponseEntity<Void> deleteCart(Long id){
        if (!cartRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public CartEntry addProductToCart(CartEntry cartEntry) {
        Optional<CartEntry> opCart = cartRepository.findById(cartEntry.getClient().getId(), cartEntry.getProduct().getId());
        if (opCart.isPresent()) {
            opCart.ifPresent(
                    value -> value.setCantInCart(value.getCantInCart() + cartEntry.getCantInCart())
            );
            return cartRepository.save(opCart.get());
        }
        return cartRepository.save(cartEntry);
    }

    public ResponseEntity<Void> deleteProductFromCart(Long id){
        if (!cartRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Product> getProductsInClientCart(Long id) {
        return cartRepository.findProductsByClientId(id);
    }

    public CartEntry getById(Long id){
        return cartRepository.getById(id);
    }
}
