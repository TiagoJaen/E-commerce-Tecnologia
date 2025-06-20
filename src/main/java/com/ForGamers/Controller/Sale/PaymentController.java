package com.ForGamers.Controller.Sale;

import com.ForGamers.Exception.PaymentAlreadyExistsException;
import com.ForGamers.Model.Sale.GetPaymentDTO;
import com.ForGamers.Model.Sale.Payment;
import com.ForGamers.Model.Sale.PaymentDTO;
import com.ForGamers.Service.Sale.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentServices;

    @Operation(summary = "Obtener listado de pagos.", description = "Devuelve una lista de todos los pagos.")
    @GetMapping
    public List<GetPaymentDTO> listPayments() {
        return paymentServices.listPayments();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Agregar un pago.", description = "No incluir id al agregar el pago.")
    @PostMapping
    public ResponseEntity<?> addPayment(@RequestBody PaymentDTO dto) {
        try {
            return ResponseEntity.ok(paymentServices.addPayment(dto));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener un pago por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id", required = false) Long id){
        try {
            return ResponseEntity.ok(paymentServices.getById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener los pagos por el id del cliente.")
    @GetMapping(value = "/client-historial", params = "client_id")
    public ResponseEntity<?> findByClientId(@RequestParam(name = "client_id", required = false) Long clientId){
        try {
            return ResponseEntity.ok(paymentServices.findByClientId(clientId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}