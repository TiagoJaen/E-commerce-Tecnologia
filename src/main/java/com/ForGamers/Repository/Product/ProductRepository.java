package com.ForGamers.Repository.Product;

import com.ForGamers.Model.Product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Optional<Product> getByName(@Param("name") String name);

    /*IgnoreCase hace que JPA ignore mayusculas y minusculas*/
    List<Product> getByNameContainingIgnoreCase(String nombre);
}
