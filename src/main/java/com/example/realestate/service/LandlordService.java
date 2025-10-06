package com.example.realestate.service;

import com.example.realestate.model.Landlord;
import com.example.realestate.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    public Landlord createLandlord(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public List<Landlord> getAllLandlords() {
        return landlordRepository.findAll();
    }

    public Optional<Landlord> getLandlordById(Long id) {
        return landlordRepository.findById(id);
    }

    public Landlord updateLandlord(Long id, Landlord landlordDetails) {
        Optional<Landlord> optionalLandlord = landlordRepository.findById(id);
        if (optionalLandlord.isPresent()) {
            Landlord landlord = optionalLandlord.get();
            landlord.setFirstName(landlordDetails.getFirstName());
            landlord.setLastName(landlordDetails.getLastName());
            landlord.setPhone(landlordDetails.getPhone());
            landlord.setEmail(landlordDetails.getEmail());
            return landlordRepository.save(landlord);
        }
        return null;
    }

    public boolean deleteLandlord(Long id) {
        if (landlordRepository.existsById(id)) {
            landlordRepository.deleteById(id);
            return true;
        }
        return false;
    }
}