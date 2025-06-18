package com.ForGamers.Service.Product;

import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.CartEntry;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Service.User.ClientService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Schema(description = "Servicio de carritos.")
@Service
public class CartService {
    private CartEntryService cartEntryService;
    private ClientService clientService;
    private ProductService productService;

    public List<CartEntry> convertCart(Cart cart) {
        List<CartEntry> list = new ArrayList<>();
        Optional<Client> clientOp = clientService.getById(cart.getClientId());

        if (clientOp.isEmpty()) return list;
        for (MutablePair<Integer, Long> pair : cart.getContents()) {
            Optional<Product> productOp = productService.getById(pair.right);
            productOp.ifPresent(product -> list.add(new CartEntry(
                    product,
                    clientOp.get(),
                    pair.left
            )));
        }
        return list;
    }
    public Cart convertCart(List<CartEntry> cartEntryList) {
        List<MutablePair<Integer, Long>> list = new ArrayList<>();

        for (CartEntry entry : cartEntryList) {
            list.add(new MutablePair<>(entry.getCantInCart(), entry.getProduct().getId()));
        }
        return new Cart(
                cartEntryList.getFirst().getClient().getId(),
                list
        );
    }

    public Cart getByClient(Long clientId) {
        return convertCart(
                cartEntryService.getEntriesByClient(clientId)
        );
    }

    public List<Cart> list() {
        return cartEntryService.list().stream()
                .collect(Collectors.groupingBy(entry -> entry.getClient().getId()))
                .values().stream()
                .map(this::convertCart)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByClient(Long clientId) {
        return cartEntryService.getProductsByClient(clientId);
    }
}
