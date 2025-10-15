package com.josephcsoftware.tsgstage2.models;

import java.math.BigDecimal;
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
@Table(name = "accumulators")
public class Accumulator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "UUID", name = "enrollment_id", nullable = false)
    private UUID enrollmentId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)", name = "type", nullable = false, length = 15)
    private AccumulatorType type;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)", name = "tier", nullable = false, length = 15)
    private NetworkTier tier;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "limit_amount",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal limitAmount;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "used_amount",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal usedAmount;
}
