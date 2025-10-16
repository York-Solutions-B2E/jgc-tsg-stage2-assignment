package com.josephcsoftware.tsgstage2.repositories;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Accumulator;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccumulatorRepository extends JpaRepository<Accumulator, UUID> {
    //
}
