package com.example.realestate.controller;

import com.example.realestate.model.Landlord;
import com.example.realestate.service.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/landlords")
@CrossOrigin(origins = "http://localhost:3000")
public class LandlordController {

    @Autowired
    private LandlordService landlordService;

    @PostMapping("/simple")
    public ResponseEntity<Landlord> createSimpleLandlord(@RequestBody Map<String, Object> request) {
        try {
            Landlord landlord = new Landlord();
            landlord.setFirstName(request.get("firstName").toString());
            landlord.setLastName(request.get("lastName").toString());
            landlord.setPhone(request.get("phone").toString());
            landlord.setEmail(request.get("email").toString());

            Landlord savedLandlord = landlordService.createLandlord(landlord);
            return new ResponseEntity<>(savedLandlord, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Остальные методы...
    @PostMapping
    public ResponseEntity<Landlord> createLandlord(@RequestBody Landlord landlord) {
        try {
            Landlord savedLandlord = landlordService.createLandlord(landlord);
            return new ResponseEntity<>(savedLandlord, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<java.util.List<Landlord>> getAllLandlords() {
        try {
            java.util.List<Landlord> landlords = landlordService.getAllLandlords();
            return new ResponseEntity<>(landlords, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Landlord> getLandlordById(@PathVariable Long id) {
        java.util.Optional<Landlord> landlord = landlordService.getLandlordById(id);
        return landlord.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Landlord> updateLandlord(@PathVariable Long id, @RequestBody Landlord landlordDetails) {
        Landlord updatedLandlord = landlordService.updateLandlord(id, landlordDetails);
        if (updatedLandlord != null) {
            return new ResponseEntity<>(updatedLandlord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLandlord(@PathVariable Long id) {
        try {
            boolean deleted = landlordService.deleteLandlord(id);
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