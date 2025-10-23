package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.models.Plan;
import com.josephcsoftware.tsgstage2.models.PlanType;
import com.josephcsoftware.tsgstage2.repositories.PlanRepository;

import org.springframework.stereotype.Service;

@Service
public class PlanService {
    
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan createPlan(int year) {
        Plan newPlan = new Plan();

        newPlan.setName("Driftwood-tier PPO");
        newPlan.setType(PlanType.PPO);
        newPlan.setNetworkName("GooberNet");
        newPlan.setPlanYear(new Integer(year));

        planRepository.save(newPlan);

        return newPlan;
    }
}
