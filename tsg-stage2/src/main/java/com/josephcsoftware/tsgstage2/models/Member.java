package com.josephcsoftware.tsgstage2.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    // Foreign key: User
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID userId;

    @Column(columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String firstName;

    @Column(columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String lastName;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dateOfBirth;

    @Null(groups = Default.class) // Use a validation group to allow this field to be optional
    @Email(message = "Invalid email format", groups = Default.class)
    @Column(columnDefinition = "VARCHAR(254)", nullable = true, length = 254)
    private String email;

    @Null(groups = Default.class)
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
             message = "Invalid phone number format",
             groups = Default.class)
    @Size(min = 8, max = 20, groups = Default.class)
    @Column(columnDefinition = "VARCHAR(20)", nullable = true, length = 20)
    private String phone;

    @Embedded
    @Column(nullable = true)
    private Address mailingAddress;

    @OneToMany
    @Column
    private List<Enrollment> enrollments;
}
