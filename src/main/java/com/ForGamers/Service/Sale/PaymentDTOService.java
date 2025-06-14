package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.*;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Service.User.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PaymentDTOService {
    private ClientService clientService;
    private OrderDTOService orderDTOService;
    private CardService cardService;

    private Client getClient(Long id) throws NoSuchElementException {
        return clientService.getById(id).
                orElseThrow(() -> new NoSuchElementException("No existe el producto"));
    }

    private Card getCard(Card card) throws NoSuchElementException{
        return cardService.getCard(card).
                orElseThrow(() -> new NoSuchElementException("Los datos de la tarjeta son invalidos"));
    }

    private List<Order> getOrders(List<OrderDTO> orders, Payment payment) {
        return orders.stream()
                .map(dto -> orderDTOService.DTOtoOrder(dto, payment))
                .toList();
    }

    private Double getTotal(OrderDTO dto) {
        return orderDTOService.getProduct(dto).getPrice() * dto.getCant();
    }

    public Payment DTOtoPayment(PaymentDTO dto) {
        Payment payment = new Payment(
                dto.getId(),
                getClient(dto.getClientId()),
                getCard(cardService.DTOtoCard(dto.getCard())),
                dto.getOrders().stream().
                        map(this::getTotal).
                        reduce(0.0, Double::sum),
                dto.getDate()
        );

        for (OrderDTO orderDTO : dto.getOrders()) {
            payment.getOrders().add(orderDTOService.DTOtoOrder(orderDTO, payment));
        }

        return payment;
    }
}