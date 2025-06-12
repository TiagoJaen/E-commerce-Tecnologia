package com.ForGamers.Controller.Sale;

import com.ForGamers.Exception.ExistentCardException;
import com.ForGamers.Model.Sale.Card;
import com.ForGamers.Model.Sale.CardDTO;
import com.ForGamers.Service.Sale.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
@Tag(name = "cards", description = "Controladora de tarjetas")
public class CardController {
    private final CardService services;

    @Operation(summary = "Obtener listado de tarjetas.", description = "Devuelve una lista de todas las tarjetas.")
    @GetMapping
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
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id", required = false) Long id){
        Optional<Card> card = services.getById(id);
        if (card.isPresent()) {
            return ResponseEntity.ok(card.get());
        }
        return ResponseEntity.notFound().build();
    }
}