package com.example.realestate.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    private String type;
    private BigDecimal price;
    private Double area;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    @JsonIgnore
    private Landlord landlord;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Lease> leases = new ArrayList<>();

    public Property() {}

    public Property(String address, String type, BigDecimal price, Double area, String description, Landlord landlord) {
        this.address = address;
        this.type = type;
        this.price = price;
        this.area = area;
        this.description = description;
        this.landlord = landlord;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Landlord getLandlord() { return landlord; }
    public void setLandlord(Landlord landlord) { this.landlord = landlord; }

    public List<Lease> getLeases() { return leases; }
    public void setLeases(List<Lease> leases) { this.leases = leases; }
}