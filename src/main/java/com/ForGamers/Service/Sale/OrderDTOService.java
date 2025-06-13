package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.OrderDTO;
import com.ForGamers.Model.Sale.Payment;
import com.ForGamers.Service.Product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class OrderDTOService {
    private final ProductService productService;

    private Product getProduct(Long id) throws NoSuchElementException{
        return productService.getById(id).
                orElseThrow(() -> new NoSuchElementException("No existe el producto"));
    }

    public Order DTOtoOrder(OrderDTO dto, Payment payment) throws NoSuchElementException {
        return new Order(
                dto.getId(),
                getProduct(dto.getProductId()),
                payment,
                dto.getCant(),
                dto.getCost()
        );
    }
}