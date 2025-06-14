package com.ForGamers.Model.Sale;

import com.ForGamers.Model.Product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Schema(description = "Clase que representa cada orden que se hace en un pago.")
public class Order {
    @Id
    @GeneratedValue
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Schema(example = "5")
    private Integer cant;

    @Schema(example = "155.07")
    private Double cost;

    public Order(Long id, Product product, Payment payment, Integer cant) {
        this.id = id;
        this.product = product;
        this.payment = payment;
        this.cant = cant;
        this.cost = product.getPrice() * cant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return this.id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}