package com.ForGamers.Model.Sale;


import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDTO {
    private Long id;
    private Long productId;

    @PositiveOrZero
    private Integer cant;
}