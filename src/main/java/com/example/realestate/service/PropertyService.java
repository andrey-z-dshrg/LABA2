package com.example.realestate.service;

import com.example.realestate.model.Property;
import com.example.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property propertyDetails) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            property.setAddress(propertyDetails.getAddress());
            property.setType(propertyDetails.getType());
            property.setPrice(propertyDetails.getPrice());
            property.setArea(propertyDetails.getArea());
            property.setDescription(propertyDetails.getDescription());
            return propertyRepository.save(property);
        }
        return null;
    }

    public boolean deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Property> getPropertiesByLandlord(Long landlordId) {
        return propertyRepository.findByLandlordId(landlordId);
    }

    public List<Property> getPropertiesByType(String type) {
        return propertyRepository.findByType(type);
    }
}