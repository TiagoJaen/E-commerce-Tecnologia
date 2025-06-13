package com.ForGamers.Model.Sale;

import com.ForGamers.Model.User.Client;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class PaymentDTO {
    private Long id;
    private Long clientId;
    private List<OrderDTO> orders;

    @PositiveOrZero
    private Double total;

    private LocalDateTime date;
}
