package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.ProviderRepository;

import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    
    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
}
