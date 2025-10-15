package com.josephcsoftware.tsgstage2.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    // Foreign key: User
    @Column(name = "user_id", columnDefinition = "UUID", nullable = false, unique = true)
    private UUID userId;

    @Size(max = 63)
    @Column(name = "first_name", columnDefinition = "VARCHAR(63)", nullable = false)
    private String firstName;

    @Size(max = 63)
    @Column(name = "last_name", columnDefinition = "VARCHAR(63)", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", columnDefinition = "DATE", nullable = false)
    private LocalDate dateOfBirth;

    @Null(groups = Default.class) // Use a validation group to allow this field to be optional
    @Size(max = 254, groups = Default.class)
    @Email(message = "Invalid email format", groups = Default.class)
    @Column(name = "email", columnDefinition = "VARCHAR(254)", nullable = true)
    private String email;

    @Null(groups = Default.class)
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
             message = "Invalid phone number format",
             groups = Default.class)
    @Size(min = 8, max = 20, groups = Default.class)
    @Column(
        name = "phone",
        nullable = true,
        columnDefinition = "VARCHAR(20)"
    )
    private String phone;

    @Embedded
    @Column(name = "mailing_address", nullable = true)
    private Address mailingAddress;

    @OneToMany(mappedBy = "member_id")
    @Column(name = "enrollments")
    private List<Enrollment> enrollments;
}
