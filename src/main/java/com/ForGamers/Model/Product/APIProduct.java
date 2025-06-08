package com.ForGamers.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class APIProduct {
    private double price;
    private String title, description, category, image;
}
