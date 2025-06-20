package com.ForGamers.Service.Sale;

import com.ForGamers.Exception.OrderAlreadyExistsException;
import com.ForGamers.Exception.PaymentAlreadyExistsException;
import com.ForGamers.Model.Sale.GetPaymentDTO;
import com.ForGamers.Model.Sale.Order;
import com.ForGamers.Model.Sale.Payment;
import com.ForGamers.Model.Sale.PaymentDTO;
import com.ForGamers.Repository.Sale.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentDTOService dtoService;
    private final OrderService orderService;

    @Transactional
    public Payment addPayment(PaymentDTO dto) throws Exception{
        Payment payment = paymentRepository.save(dtoService.DTOtoPayment(dto));
        for(Order order: payment.getOrders()) {
            orderService.addOrder(order);
        }
        return payment;
    }

    public List<GetPaymentDTO> listPayments() {
        return paymentRepository.findAll().
                stream().
                map(dtoService::PaymentToDTO).
                toList();
    }

    public List<GetPaymentDTO> findByClientId(Long clientId) throws NoSuchElementException{
        return paymentRepository.findByClientId(clientId).
                orElseThrow(() -> new NoSuchElementException("El cliente no tiene pagos asociados")).
                stream().map(dtoService::PaymentToDTO)
                .toList();
    }

    public GetPaymentDTO getById(Long id) throws NoSuchElementException {
        return dtoService.PaymentToDTO(
                paymentRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("No existe un pago con ese id")));
    }
}