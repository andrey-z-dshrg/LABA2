package com.example.realestate.controller;

import com.example.realestate.model.Payment;
import com.example.realestate.model.Lease;
import com.example.realestate.service.PaymentService;
import com.example.realestate.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap; // Добавьте этот импорт
import java.util.stream.Collectors; // Добавьте этот импорт

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private LeaseRepository leaseRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPayments() { // Измените возвращаемый тип
        try {
            List<Payment> payments = paymentService.getAllPayments();

            // Создаем упрощенные объекты чтобы избежать циклических ссылок
            List<Map<String, Object>> simplifiedPayments = payments.stream()
                    .map(payment -> {
                        Map<String, Object> paymentMap = new HashMap<>();
                        paymentMap.put("id", payment.getId());
                        paymentMap.put("amount", payment.getAmount());
                        paymentMap.put("dueDate", payment.getDueDate());
                        paymentMap.put("paymentDate", payment.getPaymentDate());
                        paymentMap.put("status", payment.getStatus());

                        // Добавляем только ID lease чтобы избежать циклических ссылок
                        if (payment.getLease() != null) {
                            Map<String, Object> leaseMap = new HashMap<>();
                            leaseMap.put("id", payment.getLease().getId());
                            paymentMap.put("lease", leaseMap);
                        }

                        return paymentMap;
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(simplifiedPayments, HttpStatus.OK); // Добавьте <>
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Создать платеж с простыми полями
    @PostMapping("/simple")
    public ResponseEntity<Payment> createSimplePayment(@RequestBody Map<String, Object> request) {
        try {
            Payment payment = new Payment();

            // Получаем ID lease из запроса
            Map<String, Object> leaseMap = (Map<String, Object>) request.get("lease");
            Long leaseId = Long.valueOf(leaseMap.get("id").toString());

            // Проверяем существование lease перед созданием
            if (!leaseRepository.existsById(leaseId)) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            // Создаем lease объект и устанавливаем ID
            Lease lease = new Lease();
            lease.setId(leaseId);
            payment.setLease(lease);

            payment.setAmount(new BigDecimal(request.get("amount").toString()));
            payment.setDueDate(LocalDate.parse(request.get("dueDate").toString()));

            // Устанавливаем статус
            String status = request.get("status").toString();
            payment.setStatus(status);

            Payment savedPayment = paymentService.createPayment(payment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Payment creation error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Остальные методы остаются без изменений...
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.createPayment(payment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
        Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
        if (updatedPayment != null) {
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id) {
        try {
            boolean deleted = paymentService.deletePayment(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lease/{leaseId}")
    public ResponseEntity<List<Payment>> getPaymentsByLease(@PathVariable Long leaseId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByLeaseId(leaseId);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Payment>> getOverduePayments() {
        try {
            List<Payment> payments = paymentService.getOverduePayments();
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-lease/{id}")
    public ResponseEntity<String> checkLease(@PathVariable Long id) {
        try {
            boolean exists = leaseRepository.existsById(id);
            return ResponseEntity.ok("Lease with ID " + id + " exists: " + exists);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error checking lease: " + e.getMessage());
        }
    }

    @PostMapping("/test")
    public ResponseEntity<String> testCreatePayment() {
        try {
            Payment payment = new Payment();

            // Создаем lease с ID (убедитесь, что lease с ID=1 существует)
            Lease lease = new Lease();
            lease.setId(1L);
            payment.setLease(lease);

            payment.setAmount(new BigDecimal("1000.00"));
            payment.setDueDate(LocalDate.of(2024, 12, 1));
            payment.setStatus("PENDING");

            Payment savedPayment = paymentService.createPayment(payment);
            return ResponseEntity.ok("Payment created with ID: " + savedPayment.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}