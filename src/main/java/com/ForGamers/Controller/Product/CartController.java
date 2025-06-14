package com.ForGamers.Controller.Product;

import com.ForGamers.Exception.ExistentProductException;
import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Repository.Product.CartRepository;
import com.ForGamers.Service.Product.CartService;
import com.ForGamers.Service.Product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/cart")
@Tag(name = "cart", description = "Operaciones relacionadas al carrito.")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //METODOS
    @Operation(summary = "Obtener lista de productos en carrito")
    @GetMapping(params = "client_id")
    public ResponseEntity<List<Product>> getProductsInCart(@RequestParam(name = "client_id") Long clientId) {
        List<Product> productos = cartService.getProductsInClientCart(clientId);
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Agregar o un producto al carrito.", description = "No incluir id al agregar un producto al carrito.")
    @PostMapping
    public ResponseEntity<?> addProductToCart(@RequestBody Cart cart) {
        try {
            Cart saved = cartService.addProductToCart(cart);
            return ResponseEntity.ok(saved);
        }catch (ExistentProductException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @Operation(summary = "Eliminar un producto del carrito por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteProductFromCart(@RequestParam(name = "id") Long id){

        return cartService.deleteProductFromCart(id);
    }
}
