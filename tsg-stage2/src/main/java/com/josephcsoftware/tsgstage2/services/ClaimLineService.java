package com.josephcsoftware.tsgstage2.services;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.models.ClaimLine;
import com.josephcsoftware.tsgstage2.repositories.ClaimLineRepository;

import org.springframework.stereotype.Service;

@Service
public class ClaimLineService {
    
    private final ClaimLineRepository claimLineRepository;

    public ClaimLineService(ClaimLineRepository claimLineRepository) {
        this.claimLineRepository = claimLineRepository;
    }

    private static BigDecimal randomCost(float min, float max) {
        float precise = ThreadLocalRandom.current().nextFloat(max - min) + min;
        float rounded = Math.round(precise * 100f);
        return BigDecimal.valueOf((long)rounded, 2);
    }

    public ClaimLine createLine(int lineNumber, UUID claimId, String reason) {
        ClaimLine newLine = new ClaimLine();

        newLine.setLineNumber(lineNumber);
        newLine.setClaimId(claimId);
        newLine.setDescription(reason);
        newLine.setCptCode(Utils.randomCode());

        long allowedAmountLong = Utils.STANDARD_ALLOWED_AMOUNT * 100;

        // Set standard allowed amount
        newLine.setAllowedAmount(BigDecimal.valueOf(allowedAmountLong, 2));

        // Set initial cost (1x to 5x of allowed amount)
        newLine.setBilledAmount(randomCost(
                                           Utils.STANDARD_ALLOWED_AMOUNT,
                                           Utils.STANDARD_ALLOWED_AMOUNT * 5f
                                           )
                                );

        // Set copay
        BigDecimal copay = randomCost(20f, 50f);
        newLine.setCopayApplied(copay);

        // Set deductible
        BigDecimal deductible = BigDecimal.valueOf(allowedAmountLong, 2);
        newLine.setDeductibleApplied(deductible);

        // None of these examples will meet the deductible,
        // so we can zero these next two values
        BigDecimal zero = BigDecimal.valueOf(0, 2);
        newLine.setCoinsuranceApplied(zero);
        newLine.setPlanPaid(zero);

        // Set the member responsibility
        BigDecimal responsibility = deductible.add(copay);
        newLine.setMemberResponsibility(responsibility);

        claimLineRepository.save(newLine);

        return newLine;
    }
}
