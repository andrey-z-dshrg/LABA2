package com.example.realestate.service;

import com.example.realestate.model.Tenant;
import com.example.realestate.model.Lease;
import com.example.realestate.repository.TenantRepository;
import com.example.realestate.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    public List<Lease> getTenantActiveLeases(Long tenantId) {
        // Упрощенный вариант - используем существующий метод findByTenantId
        List<Lease> allLeases = leaseRepository.findByTenantId(tenantId);
        // Фильтруем активные аренды
        return allLeases.stream()
                .filter(lease -> "ACTIVE".equals(lease.getStatus()))
                .toList();
    }

    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Optional<Tenant> getTenantById(Long id) {
        return tenantRepository.findById(id);
    }

    public Tenant updateTenant(Long id, Tenant tenantDetails) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(id);
        if (optionalTenant.isPresent()) {
            Tenant tenant = optionalTenant.get();
            tenant.setFirstName(tenantDetails.getFirstName());
            tenant.setLastName(tenantDetails.getLastName());
            tenant.setPhone(tenantDetails.getPhone());
            tenant.setEmail(tenantDetails.getEmail());
            return tenantRepository.save(tenant);
        }
        return null;
    }

    public boolean deleteTenant(Long id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ИСПРАВЛЕННЫЙ МЕТОД - возвращаем Optional или Tenant
    public Tenant getTenantByEmail(String email) {
        return tenantRepository.findByEmail(email)
                .orElse(null); // Возвращаем null если не найден
    }

    // Альтернативный вариант - возвращать Optional
    public Optional<Tenant> getTenantByEmailOptional(String email) {
        return tenantRepository.findByEmail(email);
    }

    public List<Tenant> getTenantByLastName(String lastName) {
        return tenantRepository.findByLastName(lastName);
    }

    public boolean hasActiveLeases(Long id) {
        List<Lease> activeLeases = getTenantActiveLeases(id);
        return !activeLeases.isEmpty();
    }

    public List<Lease> getTenantLeases(Long id) {
        return leaseRepository.findByTenantId(id);
    }
}