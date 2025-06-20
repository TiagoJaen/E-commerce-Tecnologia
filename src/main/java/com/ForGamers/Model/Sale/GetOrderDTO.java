package com.ForGamers.Model.Sale;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetOrderDTO {
    private Long id;
    private Long productId;
    private Long paymentId;

    @PositiveOrZero
    private Integer cant;
}