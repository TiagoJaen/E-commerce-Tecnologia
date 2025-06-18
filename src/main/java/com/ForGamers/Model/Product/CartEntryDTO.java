package com.ForGamers.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntryDTO {
    private Long clientId;
    private Long productId;
    private Integer quantity;
}
