package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.EnrollmentRepository;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
}
