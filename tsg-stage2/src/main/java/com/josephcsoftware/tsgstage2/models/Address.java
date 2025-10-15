package com.josephcsoftware.tsgstage2.models;

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

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
}
