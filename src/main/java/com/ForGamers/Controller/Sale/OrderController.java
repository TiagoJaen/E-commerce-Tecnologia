package com.ForGamers.Controller.Sale;

import com.ForGamers.Exception.ExistentOrderException;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Service.Sale.OrderDTOService;
import com.ForGamers.Service.Sale.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService services;

    @Operation(summary = "Obtener listado de ordenes.", description = "Devuelve una lista de todas las ordenes.")
    @GetMapping
    public List<Order> listOrders() {
        return services.listOrders();
    }

    /*@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Agregar una orden.", description = "No incluir id al agregar la orden.")
    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        try {
            return ResponseEntity.ok(services.addOrder(order)));
        }catch (ExistentOrderException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }*/

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener una orden por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id", required = false) Long id){
        Optional<Order> card = services.getById(id);
        if (card.isPresent()) {
            return ResponseEntity.ok(card.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener una orden por id del pago.")
    @GetMapping(value = "/payment", params = "payment_id")
    public ResponseEntity<?> findByPaymentId(@RequestParam(name = "payment_id", required = false) Long paymentId){
        Optional<List<Order>> payment = services.findByPaymentId(paymentId);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        }
        return ResponseEntity.notFound().build();
    }
}