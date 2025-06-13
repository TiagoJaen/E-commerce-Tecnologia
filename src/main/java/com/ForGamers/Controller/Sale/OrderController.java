package com.ForGamers.Controller.Sale;

import com.ForGamers.Exception.ExistentCardException;
import com.ForGamers.Model.Sale.Card;
import com.ForGamers.Model.Sale.CardDTO;
import com.ForGamers.Service.Sale.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @GetMapping("/all")
    public List<Card> listCards() {
        return services.listCards();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Agregar una tarjeta.", description = "No incluir id al agregar la tarjeta.")
    @PostMapping
    public ResponseEntity<?> addCard(@RequestBody @Valid CardDTO dto) {
        try {
            return ResponseEntity.ok(services.addCard(services.DTOtoCard(dto)));
        }catch (ExistentCardException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Eliminar una tarjeta por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteCard(@RequestParam(name = "id") Long id){
        return services.deleteCard(id);
    }

    //BUSCAR POR ID
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener una tarjeta por id.")
    @GetMapping(params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id", required = false) Long id){
        Optional<Card> card = services.getById(id);
        if (card.isPresent()) {
            return ResponseEntity.ok(card.get());
        }
        return ResponseEntity.notFound().build();
    }
}
