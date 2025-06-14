package com.ForGamers.Model.Sale;

import com.ForGamers.Model.User.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Schema(description = "Clase que representa a los pagos")
public class Payment {
    @Id
    @GeneratedValue
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany
    @JoinColumn(name = "card_id")
    private Card card;

    @OneToMany(mappedBy = "payment")
    private List<Order> orders;

    private Double total;

    @Schema(example = "2025/10/26")
    private LocalDateTime date;

    public Payment(Long id, Client client, Card card, Double total, LocalDateTime date) {
        this.id = id;
        this.client = client;
        this.card = card;
        this.orders = new LinkedList<>();
        this.total = total;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return this.id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}