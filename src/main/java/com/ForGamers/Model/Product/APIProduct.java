package com.ForGamers.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class APIProduct {
    private double price;
    private String title, description, category, image;
}
