package com.atm.system.initializer;

import com.atm.system.repository.DenominationRepository;
import com.atm.system.service.DenominationManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DenominationDataInitializer implements CommandLineRunner {

    private final DenominationRepository denominationRepository;
    private final DenominationManagementService managementService;

    @Override
    public void run(String... args) {

        if (denominationRepository.count() > 0) {
            return;
        }

        managementService.reset();

    }
}