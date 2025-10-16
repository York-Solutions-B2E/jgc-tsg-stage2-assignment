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

    @Column(columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String line1;
    
    @Column(columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String line2;
    
    @Column(columnDefinition = "VARCHAR(31)", nullable = false, length = 31)
    private String city;

    // IL, WA, OH, etc
    @Column(columnDefinition = "VARCHAR(2)", nullable = false, length = 2)
    private String state;
    
    @Column(columnDefinition = "VARCHAR(15)", nullable = false, length = 15)
    private String postalCode;
}
