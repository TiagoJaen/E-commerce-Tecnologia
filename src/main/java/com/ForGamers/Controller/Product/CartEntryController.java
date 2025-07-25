package com.ForGamers.Controller.Product;

import com.ForGamers.Exception.ProductAlreadyExistsException;
import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.CartEntry;
import com.ForGamers.Model.Product.CartEntryDTO;
import com.ForGamers.Service.Product.CartEntryService;
import com.ForGamers.Service.Product.CartService;
import com.ForGamers.Service.Product.ProductService;
import com.ForGamers.Service.User.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
@Tag(name = "cart", description = "Operaciones relacionadas a entradas del carrito.")
public class CartEntryController {
    private final CartEntryService cartEntryService;
    private final CartService cartService;
    private final ClientService clientService;
    private final ProductService productService;

    //METODOS
    @Operation(summary = "Obtener el carrito de un cliente")
    @GetMapping(params = "client_id")
    public ResponseEntity<?> getCartByClient(@RequestParam(name = "client_id") Long clientId) {
        if (clientService.getById(clientId).isEmpty())
            return ResponseEntity.notFound().build();

        try {
            Cart cart = cartService.getByClient(clientId);
            return ResponseEntity.ok(cart);
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok(new Cart(clientId));
        }
    }

    @Operation(summary = "Obtener la entrada de carrito de un cliente")
    @GetMapping("/entry")
    public ResponseEntity<?> getCartEntryByClient(@RequestParam(name = "client_id") Long clientId,
                                                  @RequestParam(name = "product_id") Long productId) {
        if (clientService.getById(clientId).isEmpty())
            return ResponseEntity.notFound().build();

        try {
            CartEntry cartEntry = cartEntryService.getCartEntryByClient(clientId, productId);
            return ResponseEntity.ok(cartEntry);
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok(new Cart(clientId));
        }
    }

    @Operation(summary = "Obtener todos los carritos.")
    @GetMapping("/all")
    public List<Cart> listCarts() {
        return cartService.list();
    }

    @Operation(summary = "Agregar una entrada de carrito.")
    @PostMapping
    public ResponseEntity<?> addCartEntry(@RequestBody CartEntryDTO cartEntryDTO) {
        try {
            CartEntry cartEntry = new CartEntry(
                    productService.getById(cartEntryDTO.getProductId()).get(),
                    clientService.getById(cartEntryDTO.getClientId()).get(),
                    cartEntryDTO.getQuantity()
            );

            CartEntry saved = cartEntryService.addCartEntry(cartEntry);
            return ResponseEntity.ok(saved);
        }catch (ProductAlreadyExistsException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @Operation(summary = "Eliminar una entrada del carrito por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteCartEntry(@RequestParam(name = "id") Long id){
        return cartEntryService.deleteCartEntry(id);
    }

    @Operation(summary = "Obtener el precio total de los productos en el carrito por id del cliente.")
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalProductAmountInCart(@RequestParam(name = "client_id") Long id) {
        return ResponseEntity.ok(cartService.getTotalPriceByClient(id));
    }
}
