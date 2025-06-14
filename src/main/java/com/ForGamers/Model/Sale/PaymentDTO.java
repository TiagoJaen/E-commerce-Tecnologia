package com.ForGamers.Model.Sale;

import com.ForGamers.Model.User.Client;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PaymentDTO {
    private Long id;
    private Long clientId;
    private CardDTO card;
    private List<OrderDTO> orders;
    private LocalDateTime date;

    public PaymentDTO(Long id, Long clientId, CardDTO card, LocalDateTime date) {
        this.id = id;
        this.clientId = clientId;
        this.card = card;
        this.orders = new LinkedList<>();
        this.date = date;
    }
}
