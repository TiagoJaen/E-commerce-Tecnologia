package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.OrderDTO;
import com.ForGamers.Model.Sale.Payment;
import com.ForGamers.Model.Sale.PaymentDTO;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Service.User.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PaymentDTOService {
    private ClientService clientService;
    private OrderDTOService orderDTOService;

    private Client getClient(Long id) throws NoSuchElementException {
        return clientService.getById(id).
                orElseThrow(() -> new NoSuchElementException("No existe el producto"));
    }

    private List<Order> getOrders(List<OrderDTO> orders, Payment payment) {
        return orders.stream()
                .map(dto -> orderDTOService.DTOtoOrder(dto, payment))
                .toList();
    }

    public Payment DTOtoPayment(PaymentDTO dto) {
        Payment payment = new Payment(
                dto.getId(),
                getClient(dto.getClientId()),
                new ArrayList<>(),
                dto.getTotal(),
                dto.getDate()
        );

        for (OrderDTO orderDTO : dto.getOrders()) {
            payment.getOrders().add(orderDTOService.DTOtoOrder(orderDTO, payment));
        }

        return payment;
    }
}