package com.example.realestate.repository;

import com.example.realestate.model.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByPropertyId(Long propertyId);
    List<Lease> findByTenantId(Long tenantId);
    List<Lease> findByStatus(String status);
}