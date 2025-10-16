package com.josephcsoftware.tsgstage2.repositories;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    //
}
