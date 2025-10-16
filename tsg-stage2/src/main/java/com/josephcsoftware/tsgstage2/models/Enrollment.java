package com.josephcsoftware.tsgstage2.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID memberId;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID planId;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate coverageStart;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate coverageEnd;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany
    @Column
    private List<Accumulator> accumulators;
}
