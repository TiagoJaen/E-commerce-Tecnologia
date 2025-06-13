package com.ForGamers.Model.Sale;

import com.ForGamers.Model.User.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
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
    @Schema(example = "1")
    private Client client;

    @OneToMany(mappedBy = "payment")
    private List<Order> orders;

    @PositiveOrZero
    private Double total;

    @Schema(example = "2025/10/26")
    private LocalDateTime date;

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