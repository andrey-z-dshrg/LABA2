package com.example.realestate.repository;

import com.example.realestate.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByLeaseId(Long leaseId);
    List<Payment> findByStatus(String status);
}