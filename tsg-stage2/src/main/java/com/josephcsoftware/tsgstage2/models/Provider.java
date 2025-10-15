package com.josephcsoftware.tsgstage2.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "VARCHAR(127)", name = "name", nullable = false, length = 127)
    private String name;

    @Column(columnDefinition = "TEXT", name = "specialty", nullable = false)
    private String specialty;

    @Embedded
    @Column(name = "address", nullable = false)
    private Address address;
    
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
             message = "Invalid phone number format")
    @Size(min = 8, max = 20)
    @Column(columnDefinition = "VARCHAR(20)", name = "phone", nullable = false, length = 20)
    private String phone;
}
