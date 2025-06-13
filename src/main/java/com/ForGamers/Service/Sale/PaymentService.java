package com.ForGamers.Service.Sale;

import com.ForGamers.Exception.ExistentOrderException;
import com.ForGamers.Exception.ExistentPaymentException;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.Payment;
import com.ForGamers.Repository.Sale.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public Payment addPayment(Payment payment) {
        if (paymentRepository.findById(payment.getId()).isPresent()) {
            throw new ExistentPaymentException("Ya existe la orden.");
        }
        for(Order order: payment.getOrders()) {
            orderService.addOrder(order);
        }
        return paymentRepository.save(payment);
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
