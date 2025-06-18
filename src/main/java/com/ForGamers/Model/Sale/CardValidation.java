package com.ForGamers.Model.Sale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private Long cardId;

    private String numberHashcode;
    private String entityHashcode;
}