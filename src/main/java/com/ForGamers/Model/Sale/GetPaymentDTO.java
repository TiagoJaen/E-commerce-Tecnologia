package com.ForGamers.Model.Sale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Getter
public class GetPaymentDTO {
    private Long id;
    private Long clientId;
    private Long cardId;
    private List<GetOrderDTO> orders;
    private LocalDateTime date;

    public GetPaymentDTO(Long id, Long clientId, Long cardId, LocalDateTime date) {
        this.id = id;
        this.clientId = clientId;
        this.cardId = cardId;
        this.orders = new LinkedList<>();
        this.date = date;
    }
}