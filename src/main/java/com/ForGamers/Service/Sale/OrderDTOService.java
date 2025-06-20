package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.GetOrderDTO;
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

    public Product getProduct(OrderDTO dto) throws NoSuchElementException{
        return productService.getById(dto.getProductId()).
                orElseThrow(() -> new NoSuchElementException("No existe el producto"));
    }

    public Order DTOtoOrder(OrderDTO dto, Payment payment) throws NoSuchElementException {
        return new Order(
                dto.getId(),
                getProduct(dto),
                payment,
                dto.getCant()
                );
    }

    public GetOrderDTO OrderToDTO(Order order) {
        return new GetOrderDTO(
                order.getId(),
                order.getProduct().getId(),
                order.getPayment().getId(),
                order.getCant()
        );
    }
}