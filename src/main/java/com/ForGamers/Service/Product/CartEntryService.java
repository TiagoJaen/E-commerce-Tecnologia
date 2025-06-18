package com.ForGamers.Service.Product;

import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.CartEntry;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Repository.Product.CartRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Schema(description = "Servicio de entradas de carritos.")
@Service
public class CartEntryService {
    private final CartRepository cartRepository;

    public ResponseEntity<Void> deleteCartEntry(Long id){
        if (!cartRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public CartEntry addCartEntry(CartEntry cartEntry) {
        Optional<CartEntry> opCart = cartRepository.findById(cartEntry.getClient().getId(), cartEntry.getProduct().getId());
        if (opCart.isEmpty()) return cartRepository.save(cartEntry);
        opCart.ifPresent(
                value -> value.setCantInCart(value.getCantInCart() + cartEntry.getCantInCart())
        );
        return cartRepository.save(opCart.get());
    }
    public List<CartEntry> addProductsToCart(List<CartEntry> list) {
        for (CartEntry entry : list)
            addCartEntry(entry);
        return list;
    }

    public ResponseEntity<Void> deleteCartEntriesByClient(Long clientId) {
        if (!cartRepository.findProductsByClientId(clientId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        for (CartEntry entry : cartRepository.findEntriesByClientId(clientId))
            cartRepository.deleteById(entry.getId());
        return ResponseEntity.noContent().build();
    }

    public List<CartEntry> list() {
        return cartRepository.findAll();
    }

    public void modify(Long id, CartEntry t) {
        t.setId(id);
        cartRepository.save(t);
    }

    public List<CartEntry> getCartEntriesByClient(Long clientId) {
        return cartRepository.findEntriesByClientId(clientId);
    }

    public List<Product> getProductsByClient(Long clientId) {
        return cartRepository.findProductsByClientId(clientId);
    }

    public Optional<CartEntry> getById(Long id){
        return cartRepository.findById(id);
    }
}
