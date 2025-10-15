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
    @Column(columnDefinition = "UUID", name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "UUID", name = "member_id", nullable = false)
    private UUID memberId;

    @Column(columnDefinition = "UUID", name = "plan_id", nullable = false)
    private UUID planId;

    @Column(columnDefinition = "DATE", name = "coverage_start", nullable = false)
    private LocalDate coverageStart;

    @Column(columnDefinition = "DATE", name = "coverage_end", nullable = false)
    private LocalDate coverageEnd;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany
    @Column(name = "accumulators")
    private List<Accumulator> accumulators;
}
