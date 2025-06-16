package com.ForGamers.Repository.Product;

import com.ForGamers.Model.Product.CartEntry;
import com.ForGamers.Model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntry, Long >{
    @Query("SELECT c.product FROM Cart c WHERE c.client.id = :client_Id")
    List<Product> findProductsByClientId(@Param("client_Id")Long client_Id);

    @Query("SELECT c FROM Cart c WHERE c.product.id = :product_Id AND c.client.id = :client_Id")
    Optional<CartEntry> findById(@Param("client_Id")Long client_Id,
                                 @Param("product_Id")Long product_Id);

}
