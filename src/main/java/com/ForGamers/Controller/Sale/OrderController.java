package com.ForGamers.Controller.Sale;

import com.ForGamers.Exception.OrderAlreadyExistsException;
import com.ForGamers.Model.Sale.GetOrderDTO;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Service.Sale.OrderDTOService;
import com.ForGamers.Service.Sale.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService services;

    @Operation(summary = "Obtener listado de ordenes.", description = "Devuelve una lista de todas las ordenes.")
    @GetMapping
    public List<GetOrderDTO> listOrders() {
       return services.listOrders();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener una orden por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id", required = false) Long id){
        try {
            return ResponseEntity.ok(services.getById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener una orden por id del pago.")
    @GetMapping(value = "/payment", params = "payment_id")
    public ResponseEntity<?> findByPaymentId(@RequestParam(name = "payment_id", required = false) Long paymentId){
        try {
            return ResponseEntity.ok(services.findByPaymentId(paymentId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}