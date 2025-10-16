package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.PlanRepository;

import org.springframework.stereotype.Service;

@Service
public class PlanService {
    
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }
}
