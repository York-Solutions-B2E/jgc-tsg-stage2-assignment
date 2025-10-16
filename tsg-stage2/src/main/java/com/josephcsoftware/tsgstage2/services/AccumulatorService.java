package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.AccumulatorRepository;

import org.springframework.stereotype.Service;

@Service
public class AccumulatorService {
    
    private final AccumulatorRepository accumulatorRepository;

    public AccumulatorService(AccumulatorRepository accumulatorRepository) {
        this.accumulatorRepository = accumulatorRepository;
    }
}
