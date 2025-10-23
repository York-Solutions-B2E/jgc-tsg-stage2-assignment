package com.josephcsoftware.tsgstage2.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.models.Accumulator;
import com.josephcsoftware.tsgstage2.models.AccumulatorType;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.NetworkTier;
import com.josephcsoftware.tsgstage2.repositories.AccumulatorRepository;

import org.springframework.stereotype.Service;

@Service
public class AccumulatorService {
    
    private final AccumulatorRepository accumulatorRepository;

    public AccumulatorService(AccumulatorRepository accumulatorRepository) {
        this.accumulatorRepository = accumulatorRepository;
    }

    public Accumulator createAccumulator(
                                         AccumulatorType type,
                                         NetworkTier tier,
                                         UUID enrollmentId,
                                         ArrayList<Claim> claims
                                         ) {
        Accumulator newAccumulator = new Accumulator();

        boolean oop = (type == AccumulatorType.OOP_MAX);

        newAccumulator.setEnrollmentId(enrollmentId);
        newAccumulator.setType(type);
        newAccumulator.setTier(tier);

        BigDecimal usedAmount = BigDecimal.valueOf(0, 2);
        BigDecimal limitAmount = BigDecimal.valueOf(oop ?
                                                    Utils.STANDARD_OOP_MAXIMUM :
                                                    Utils.STANDARD_ANNUAL_DEDUCTIBLE,
                                                    2
                                                    );

        // Accumulate from claims during the year
        if (claims != null && claims.size() > 0) {
            for (int i = 0; i < claims.size(); i++) {
                Claim claim = claims.get(i);
                usedAmount = usedAmount.add(claim.getTotalMemberResponsibility());
            }
        }

        // Set amounts
        newAccumulator.setLimitAmount(limitAmount);
        newAccumulator.setUsedAmount(usedAmount);

        accumulatorRepository.save(newAccumulator);

        return newAccumulator;
    }
}
