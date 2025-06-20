package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Model.Sale.*;
import com.ForGamers.Model.User.Client;
import com.ForGamers.Service.User.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return cardService.getCard(card.getHashcode()).
                orElseThrow(() -> new NoSuchElementException("Los datos de la tarjeta son invalidos"));
    }

    private Double getTotal(OrderDTO dto) {
        return orderDTOService.getProduct(dto).getPrice() * dto.getCant();
    }

    public Payment DTOtoPayment(PaymentDTO dto) throws Exception{
        Payment payment = new Payment(
                dto.getId(),
                getClient(dto.getClientId()),
                getCard(cardService.getDtoService().DTOtoCard(dto.getCard())),
                dto.getOrders().stream().
                        map(this::getTotal).
                        reduce(0.0, Double::sum),
                LocalDateTime.now()
        );

        for (OrderDTO orderDTO : dto.getOrders()) {
            payment.getOrders().add(orderDTOService.DTOtoOrder(orderDTO, payment));
        }

        return payment;
    }

    public GetPaymentDTO PaymentToDTO(Payment payment) {
        GetPaymentDTO dto = new GetPaymentDTO(
                payment.getId(),
                payment.getClient().getId(),
                payment.getCard().getId(),
                LocalDateTime.now(),
                payment.getTotal()
        );

        for(Order order: payment.getOrders()) {
            dto.getOrders().add(orderDTOService.OrderToDTO(order));
        }

        return dto;
    }
}