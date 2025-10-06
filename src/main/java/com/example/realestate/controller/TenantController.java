package com.example.realestate.controller;

import com.example.realestate.model.Tenant;
import com.example.realestate.model.Lease;
import com.example.realestate.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
@CrossOrigin(origins = "http://localhost:3000")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping("/simple")
    public ResponseEntity<Tenant> createSimpleTenant(@RequestBody Map<String, Object> request) {
        try {
            Tenant tenant = new Tenant();
            tenant.setFirstName(request.get("firstName").toString());
            tenant.setLastName(request.get("lastName").toString());
            tenant.setPhone(request.get("phone").toString());
            tenant.setEmail(request.get("email").toString());

            Tenant savedTenant = tenantService.createTenant(tenant);
            return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        try {
            Tenant savedTenant = tenantService.createTenant(tenant);
            return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        try {
            List<Tenant> tenants = tenantService.getAllTenants();
            return new ResponseEntity<>(tenants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long id) {
        Optional<Tenant> tenant = tenantService.getTenantById(id);
        return tenant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenantDetails) {
        Tenant updatedTenant = tenantService.updateTenant(id, tenantDetails);
        if (updatedTenant != null) {
            return new ResponseEntity<>(updatedTenant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTenant(@PathVariable Long id) {
        try {
            boolean deleted = tenantService.deleteTenant(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}