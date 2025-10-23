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

    private static float randomCost(float min, float max) {
        float precise = ThreadLocalRandom.current().nextFloat(max - min) + min;
        float rounded = Math.round(precise * 100f) / 100f;
        return rounded;
    }

    public ClaimLine createLine(int lineNumber, UUID claimId, String reason) {
        ClaimLine newLine = new ClaimLine();

        newLine.setLineNumber(lineNumber);
        newLine.setClaimId(claimId);
        newLine.setDescription(reason);
        newLine.setCptCode(Utils.randomCode());

        // Set standard allowed amount
        newLine.setAllowedAmount(BigDecimal.valueOf(Utils.STANDARD_ALLOWED_AMOUNT));

        // Set initial cost (1x to 5x of allowed amount)
        float initialCost = randomCost(
                                      (float)Utils.STANDARD_ALLOWED_AMOUNT,
                                      (float)Utils.STANDARD_ALLOWED_AMOUNT * 5f
                                      );
        newLine.setBilledAmount(BigDecimal.valueOf(initialCost));

        // Set copay
        float copay = randomCost(20f, 50f);
        newLine.setCopayApplied(BigDecimal.valueOf(copay));

        // Set deductible
        newLine.setDeductibleApplied(BigDecimal.valueOf(Utils.STANDARD_ALLOWED_AMOUNT));

        // None of these examples will meet the deductible,
        // so we can zero these next two values
        newLine.setCoinsuranceApplied(BigDecimal.valueOf(0));
        newLine.setPlanPaid(BigDecimal.valueOf(0));

        // Set the member responsibility
        newLine.setMemberResponsibility(BigDecimal.valueOf(Utils.STANDARD_ALLOWED_AMOUNT + copay));

        claimLineRepository.save(newLine);

        return newLine;
    }
}
