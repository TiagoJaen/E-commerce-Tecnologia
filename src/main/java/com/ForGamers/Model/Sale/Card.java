package com.ForGamers.Model.Sale;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description = "Clase que representa a las tarjetas que van a estar cargadas en el sistema")
public class Card {
    @Id
    @GeneratedValue
    @Schema(example = "1")
    private Long id;

    @Schema(example = "pepe")
    private String holder;

    @Schema(example = "1111111111111111")
    private String number;

    @Schema(example = "05/27")
    private String expirationDate;

    @Schema(example = "123")
    private String cvv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return this.number.equals(card.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", holder='" + holder + '\'' +
                ", number='" + number + '\'' +
                ", fecha='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}