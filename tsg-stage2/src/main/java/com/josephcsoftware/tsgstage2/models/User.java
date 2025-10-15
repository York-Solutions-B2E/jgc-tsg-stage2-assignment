package com.josephcsoftware.tsgstage2.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    // Auth provider, like Google or Okta
    @Size(max = 31)
    @Column(name = "auth_provider", columnDefinition = "VARCHAR(31)", nullable = false)
    private String authProvider;

    // OIDC subject
    @Column(name = "auth_sub", columnDefinition = "TEXT", nullable = false)
    private String authSub;

    @Size(max = 254)
    @Email(message = "Invalid email format")
    @Column(name = "email", columnDefinition = "VARCHAR(254)", nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
