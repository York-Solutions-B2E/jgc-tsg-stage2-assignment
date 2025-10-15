package com.josephcsoftware.tsgstage2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Column(name = "address_line1", columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String line1;
    
    @Column(name = "address_line2", columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String line2;
    
    @Column(name = "address_city", columnDefinition = "VARCHAR(31)", nullable = false, length = 31)
    private String city;

    // IL, WA, OH, etc
    @Column(name = "address_state", columnDefinition = "VARCHAR(2)", nullable = false, length = 2)
    private String state;
    
    @Column(name = "address_postal_code", columnDefinition = "VARCHAR(15)", nullable = false, length = 15)
    private String postalCode;
}
