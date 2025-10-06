package com.example.realestate.service;

import com.example.realestate.model.Payment;
import com.example.realestate.model.Lease;
import com.example.realestate.repository.PaymentRepository;
import com.example.realestate.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    public Payment createPayment(Payment payment) {
        // Проверяем существование lease
        if (payment.getLease() == null || payment.getLease().getId() == null) {
            throw new RuntimeException("Lease is required");
        }

        Lease lease = leaseRepository.findById(payment.getLease().getId())
                .orElseThrow(() -> new RuntimeException("Lease not found with id: " + payment.getLease().getId()));

        // Устанавливаем проверенный lease
        payment.setLease(lease);

        return paymentRepository.save(payment);
    }

    // Остальные методы...
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            // Обновляем lease если предоставлен
            if (paymentDetails.getLease() != null && paymentDetails.getLease().getId() != null) {
                Lease lease = leaseRepository.findById(paymentDetails.getLease().getId())
                        .orElseThrow(() -> new RuntimeException("Lease not found"));
                payment.setLease(lease);
            }

            payment.setAmount(paymentDetails.getAmount());
            payment.setPaymentDate(paymentDetails.getPaymentDate());
            payment.setDueDate(paymentDetails.getDueDate());
            payment.setStatus(paymentDetails.getStatus());

            return paymentRepository.save(payment);
        }
        return null;
    }

    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Payment> getPaymentsByLeaseId(Long leaseId) {
        return paymentRepository.findByLeaseId(leaseId);
    }

    public List<Payment> getOverduePayments() {
        return paymentRepository.findByStatus("OVERDUE");
    }
}