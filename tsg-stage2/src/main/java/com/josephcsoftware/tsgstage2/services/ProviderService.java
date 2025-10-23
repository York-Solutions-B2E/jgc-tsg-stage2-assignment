package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.models.Provider;
import com.josephcsoftware.tsgstage2.repositories.ProviderRepository;

import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    
    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider createProvider() {
        Provider newProvider = new Provider();
        newProvider.setName("Dr. Leslie Wemsie");
        newProvider.setSpecialty("Treating all surreal illnesses and hysterical injuries");
        newProvider.setAddress(Utils.randomProviderAddress());
        newProvider.setPhone("(555) 555-5555");
        providerRepository.save(newProvider);
        return newProvider;
    }
}
