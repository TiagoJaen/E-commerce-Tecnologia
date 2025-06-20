package com.ForGamers.Service.Sale;

import com.ForGamers.Exception.OrderAlreadyExistsException;
import com.ForGamers.Exception.PaymentAlreadyExistsException;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.GetOrderDTO;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.OrderDTO;
import com.ForGamers.Model.Sale.Payment;
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

    public Order addOrder(Order order) throws PaymentAlreadyExistsException, NoSuchElementException {
        return orderRepository.save(order);
    }

    public List<GetOrderDTO> listOrders() {
        return orderRepository.findAll().
                stream().
                map(dtoService::OrderToDTO).
                toList();
    }

    public GetOrderDTO getById(Long id) throws NoSuchElementException {
        return dtoService.OrderToDTO(
                orderRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("No existe el pago"))
        );
    }

    public List<GetOrderDTO> findByPaymentId(Long paymentId) {
        return orderRepository.findByPaymentId(paymentId).
                orElseThrow(() -> new NoSuchElementException("El pago no existe")).
                stream().map(dtoService::OrderToDTO)
                .toList();
    }
}