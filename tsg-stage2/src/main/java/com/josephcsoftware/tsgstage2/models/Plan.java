package com.josephcsoftware.tsgstage2.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    // Plan name, like "Gold PPO"
    @Column(columnDefinition = "VARCHAR(23)", nullable = false, unique = true, length = 23)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(7)", nullable = false, length = 7)
    private PlanType type;

    // Network name, like "Prime"
    @Column(columnDefinition = "VARCHAR(63)", nullable = false, length = 63)
    private String networkName;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer planYear;
}
