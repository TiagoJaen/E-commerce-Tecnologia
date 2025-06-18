package com.ForGamers.Model.Sale;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardValidation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "card_id")
    @OneToOne(mappedBy = "")
    private Card card;

    private String numberHashcode;
    private String entityHashcode;
}