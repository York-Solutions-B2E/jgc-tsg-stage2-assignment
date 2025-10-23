package com.josephcsoftware.tsgstage2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Accumulator;
import com.josephcsoftware.tsgstage2.models.AccumulatorType;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.Enrollment;
import com.josephcsoftware.tsgstage2.models.NetworkTier;
import com.josephcsoftware.tsgstage2.models.PlanType;
import com.josephcsoftware.tsgstage2.repositories.EnrollmentRepository;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;

    private final AccumulatorService accumulatorService;

    public EnrollmentService(
                             EnrollmentRepository enrollmentRepository,
                             AccumulatorService accumulatorService
                             ) {
        this.enrollmentRepository = enrollmentRepository;
        this.accumulatorService = accumulatorService;
    }

    public Enrollment createEnrollment(
                                       UUID memberId,
                                       UUID planId,
                                       int year,
                                       boolean active,
                                       ArrayList<Claim> claims
                                       ) {
        Enrollment newEnrollment = new Enrollment();

        newEnrollment.setMemberId(memberId);
        newEnrollment.setPlanId(planId);
        newEnrollment.setCoverageStart(LocalDate.ofYearDay(year, 0));
        newEnrollment.setCoverageStart(LocalDate.ofYearDay(year + 1, 0));
        newEnrollment.setActive(active);

        ArrayList<Accumulator> accumulators = new ArrayList<Accumulator>();

        enrollmentRepository.save(newEnrollment);

        UUID enrollmentId = newEnrollment.getId();

        // Add accumulators
        accumulators.add(accumulatorService.createAccumulator(
                                                              AccumulatorType.DEDUCTIBLE,
                                                              NetworkTier.IN_NETWORK,
                                                              enrollmentId,
                                                              claims
                                                              ));
        accumulators.add(accumulatorService.createAccumulator(
                                                              AccumulatorType.OOP_MAX,
                                                              NetworkTier.IN_NETWORK,
                                                              enrollmentId,
                                                              claims
                                                              ));
        accumulators.add(accumulatorService.createAccumulator(
                                                              AccumulatorType.DEDUCTIBLE,
                                                              NetworkTier.OUT_OF_NETWORK,
                                                              enrollmentId,
                                                              claims
                                                              ));
        accumulators.add(accumulatorService.createAccumulator(
                                                              AccumulatorType.OOP_MAX,
                                                              NetworkTier.OUT_OF_NETWORK,
                                                              enrollmentId,
                                                              claims
                                                              ));

        enrollmentRepository.save(newEnrollment);

        return newEnrollment;
    }
}
