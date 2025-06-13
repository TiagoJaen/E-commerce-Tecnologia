package com.ForGamers.Service.Sale;

import com.ForGamers.Exception.ExistentOrderException;
import com.ForGamers.Exception.ExistentPaymentException;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.OrderDTO;
import com.ForGamers.Repository.Sale.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDTOService dtoService;

    public Order addOrder(OrderDTO dto) throws ExistentPaymentException, NoSuchElementException {
        if (orderRepository.findById(dto.getId()).isPresent()) {
            throw new ExistentOrderException("Ya existe la orden.");
        }
        return orderRepository.save(dtoService.DTOtoOrder(dto));
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(Long id){
        return orderRepository.findById(id);
    }

    public Optional<List<Order>> findByPaymentId(Long paymentId) {
        return orderRepository.findByPaymentId(paymentId);
    }
}