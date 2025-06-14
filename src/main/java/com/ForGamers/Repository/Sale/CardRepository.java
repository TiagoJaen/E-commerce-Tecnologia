package com.ForGamers.Repository.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.number = :number")
    Optional<Product> getByNumber(@Param("number") String number);

    @Query("SELECT c FROM card c WHERE c = :card")
    Optional<Card> getCard(@Param("card") Card card);
}