package com.example.realestate.controller;

import com.example.realestate.model.Lease;
import com.example.realestate.model.Property;
import com.example.realestate.model.Tenant;
import com.example.realestate.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/leases")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    // Создать аренду с объектом Lease
    @PostMapping
    public ResponseEntity<Lease> createLease(@RequestBody Lease lease) {
        try {
            Lease savedLease = leaseService.createLease(lease);
            return new ResponseEntity<>(savedLease, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Создать аренду с простыми полями (ОБНОВЛЕННЫЙ МЕТОД)
    @PostMapping("/simple")
    public ResponseEntity<Lease> createSimpleLease(@RequestBody Map<String, Object> request) {
        try {
            Lease lease = new Lease();

            // Создаем объекты Property и Tenant с ID
            Property property = new Property();
            property.setId(Long.valueOf(request.get("propertyId").toString()));
            lease.setProperty(property);

            Tenant tenant = new Tenant();
            tenant.setId(Long.valueOf(request.get("tenantId").toString()));
            lease.setTenant(tenant);

            lease.setStartDate(LocalDate.parse(request.get("startDate").toString()));
            lease.setEndDate(LocalDate.parse(request.get("endDate").toString()));
            lease.setMonthlyRent(new BigDecimal(request.get("monthlyRent").toString()));

            if (request.containsKey("status")) {
                lease.setStatus(request.get("status").toString());
            }

            Lease savedLease = leaseService.createLease(lease);
            return new ResponseEntity<>(savedLease, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Тестовый метод для создания аренды (ОБНОВЛЕННЫЙ)
    @PostMapping("/test")
    public ResponseEntity<String> testCreateLease() {
        try {
            Lease lease = new Lease();

            // Создаем объекты Property и Tenant с ID
            Property property = new Property();
            property.setId(1L);
            lease.setProperty(property);

            Tenant tenant = new Tenant();
            tenant.setId(1L);
            lease.setTenant(tenant);

            lease.setStartDate(LocalDate.of(2024, 1, 1));
            lease.setEndDate(LocalDate.of(2024, 12, 31));
            lease.setMonthlyRent(new BigDecimal("28000.00"));

            Lease savedLease = leaseService.createLease(lease);
            return ResponseEntity.ok("Lease created with ID: " + savedLease.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Остальные методы остаются без изменений
    @GetMapping
    public ResponseEntity<List<Lease>> getAllLeases() {
        try {
            List<Lease> leases = leaseService.getAllLeases();
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lease> getLeaseById(@PathVariable Long id) {
        Optional<Lease> lease = leaseService.getLeaseById(id);
        return lease.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lease> updateLease(@PathVariable Long id, @RequestBody Lease leaseDetails) {
        Lease updatedLease = leaseService.updateLease(id, leaseDetails);
        if (updatedLease != null) {
            return new ResponseEntity<>(updatedLease, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLease(@PathVariable Long id) {
        try {
            boolean deleted = leaseService.deleteLease(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Lease>> getLeasesByProperty(@PathVariable Long propertyId) {
        try {
            List<Lease> leases = leaseService.getLeasesByPropertyId(propertyId);
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Lease>> getLeasesByTenant(@PathVariable Long tenantId) {
        try {
            List<Lease> leases = leaseService.getLeasesByTenantId(tenantId);
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Lease>> getActiveLeases() {
        try {
            List<Lease> leases = leaseService.getActiveLeases();
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}