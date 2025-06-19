package com.ForGamers.Service.Sale;

import com.ForGamers.Exception.OrderAlreadyExistsException;
import com.ForGamers.Exception.PaymentAlreadyExistsException;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.Payment;
import com.ForGamers.Model.Sale.PaymentDTO;
import com.ForGamers.Repository.Sale.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentDTOService dtoService;
    private final OrderService orderService;

    public Payment addPayment(PaymentDTO dto) throws NoSuchElementException {
        Payment payment = paymentRepository.save(dtoService.DTOtoPayment(dto));
        for(Order order: payment.getOrders()) {
            orderService.addOrder(order);
        }
        return payment;
    }

    public List<Payment> listPayments() {
        return paymentRepository.findAll();
    }

    public Optional<List<Payment>> findByClientId(Long clientId) {
        return paymentRepository.findByClientId(clientId);
    }

    public Optional<Payment> getById(Long id){
        return paymentRepository.findById(id);
    }
}
