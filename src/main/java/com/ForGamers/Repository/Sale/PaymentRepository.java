package com.ForGamers.Repository.Sale;

import com.ForGamers.Model.Sale.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.client.id = :client_id")
    Optional<List<Payment>> findByClientId(@Param("client_id") Long clientId);
}