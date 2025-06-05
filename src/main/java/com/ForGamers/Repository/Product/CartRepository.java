package com.ForGamers.Repository.Product;

import com.ForGamers.Model.Product.Cart;
import com.ForGamers.Model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long >{
    @Query("SELECT c.product FROM Cart c WHERE c.client.id = :client_Id")
    List<Product> findProductsByClientId(Long client_Id);
}
