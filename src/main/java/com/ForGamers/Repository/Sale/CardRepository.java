package com.ForGamers.Repository.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.hashcode = :hashcode"
    )
    Optional<Card> getCard(@Param("hashcode") int hashcode);
}