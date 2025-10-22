package com.josephcsoftware.tsgstage2.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    // Auth provider, like Google or Okta
    @Column(columnDefinition = "VARCHAR(31)", nullable = false, length = 31)
    private String authProvider;

    // OIDC subject
    @Column(columnDefinition = "VARCHAR(255)", nullable = false, length = 255)
    private String authSub;

    @Email(message = "Invalid email format")
    @Column(columnDefinition = "VARCHAR(254)", nullable = false, unique = true, length = 254)
    private String email;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    /*public User(String authProvider, String authSub, String email) {
        this.authProvider = authProvider;
        this.authSub = authSub;
        this.email = email;
        }*/
}
