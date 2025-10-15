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
    @Column(columnDefinition = "UUID", name = "id", nullable = false, unique = true)
    private UUID id;

    // Plan name, like "Gold PPO"
    @Column(columnDefinition = "VARCHAR(23)", name = "name", nullable = false, unique = true, length = 23)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(7)", name = "plan_type", nullable = false, length = 7)
    private PlanType type;

    // Network name, like "Prime"
    @Column(columnDefinition = "VARCHAR(63)", name = "network_name", nullable = false, length = 63)
    private String networkName;

    @Column(columnDefinition = "INTEGER", name = "plan_year", nullable = false)
    private Integer planYear;
}
