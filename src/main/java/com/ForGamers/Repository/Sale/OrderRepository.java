package com.ForGamers.Repository.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.payment_id = :payment_id")
    List<Product> findByPaymentId(@Param("payment_id")Long paymentId);
}