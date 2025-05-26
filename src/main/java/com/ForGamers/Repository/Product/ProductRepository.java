package com.ForGamers.Repository.Product;

import com.ForGamers.Model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
