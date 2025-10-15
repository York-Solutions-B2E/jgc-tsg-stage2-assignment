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
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", name = "id", nullable = false, unique = true)
    private UUID id;

    // Auth provider, like Google or Okta
    @Column(columnDefinition = "VARCHAR(31)", name = "auth_provider", nullable = false, length = 31)
    private String authProvider;

    // OIDC subject
    @Column(columnDefinition = "VARCHAR(255)", name = "auth_sub", nullable = false, length = 255)
    private String authSub;

    @Email(message = "Invalid email format")
    @Column(columnDefinition = "VARCHAR(254)", name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
