package com.ForGamers.Controller.Product;

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
    @GetMapping("/{clientId}/productos")
    public ResponseEntity<List<Product>> getProductsInCart(@PathVariable Long clientId) {
        List<Product> productos = cartService.getProductsInClientCart(clientId);
        return ResponseEntity.ok(productos);
    }

}
