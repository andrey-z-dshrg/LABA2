package com.example.realestate.service;

import com.example.realestate.model.Lease;
import com.example.realestate.model.Property;
import com.example.realestate.model.Tenant;
import com.example.realestate.repository.LeaseRepository;
import com.example.realestate.repository.PropertyRepository;
import com.example.realestate.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public Lease createLease(Lease lease) {
        // Проверяем существование property
        if (lease.getProperty() == null || lease.getProperty().getId() == null) {
            throw new RuntimeException("Property is required");
        }

        Property property = propertyRepository.findById(lease.getProperty().getId())
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + lease.getProperty().getId()));

        // Проверяем существование tenant
        if (lease.getTenant() == null || lease.getTenant().getId() == null) {
            throw new RuntimeException("Tenant is required");
        }

        Tenant tenant = tenantRepository.findById(lease.getTenant().getId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + lease.getTenant().getId()));

        // Устанавливаем проверенные объекты
        lease.setProperty(property);
        lease.setTenant(tenant);

        // Проверяем даты
        if (lease.getStartDate() == null || lease.getEndDate() == null) {
            throw new RuntimeException("Start date and end date are required");
        }
        if (lease.getStartDate().isAfter(lease.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        return leaseRepository.save(lease);
    }

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    public Optional<Lease> getLeaseById(Long id) {
        return leaseRepository.findById(id);
    }

    public Lease updateLease(Long id, Lease leaseDetails) {
        Optional<Lease> optionalLease = leaseRepository.findById(id);
        if (optionalLease.isPresent()) {
            Lease lease = optionalLease.get();

            // Обновляем property если предоставлен
            if (leaseDetails.getProperty() != null && leaseDetails.getProperty().getId() != null) {
                Property property = propertyRepository.findById(leaseDetails.getProperty().getId())
                        .orElseThrow(() -> new RuntimeException("Property not found"));
                lease.setProperty(property);
            }

            // Обновляем tenant если предоставлен
            if (leaseDetails.getTenant() != null && leaseDetails.getTenant().getId() != null) {
                Tenant tenant = tenantRepository.findById(leaseDetails.getTenant().getId())
                        .orElseThrow(() -> new RuntimeException("Tenant not found"));
                lease.setTenant(tenant);
            }

            lease.setStartDate(leaseDetails.getStartDate());
            lease.setEndDate(leaseDetails.getEndDate());
            lease.setMonthlyRent(leaseDetails.getMonthlyRent());
            lease.setStatus(leaseDetails.getStatus());

            return leaseRepository.save(lease);
        }
        return null;
    }

    public boolean deleteLease(Long id) {
        if (leaseRepository.existsById(id)) {
            leaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Lease> getLeasesByPropertyId(Long propertyId) {
        return leaseRepository.findByPropertyId(propertyId);
    }

    public List<Lease> getLeasesByTenantId(Long tenantId) {
        return leaseRepository.findByTenantId(tenantId);
    }

    public List<Lease> getActiveLeases() {
        return leaseRepository.findByStatus("ACTIVE");
    }
}