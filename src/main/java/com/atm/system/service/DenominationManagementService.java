package com.atm.system.service;

import com.atm.system.dto.DenominationStock;
import com.atm.system.model.Denomination;
import com.atm.system.model.DenominationType;
import com.atm.system.repository.DenominationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DenominationManagementService {

    private final DenominationRepository denominationRepository;

    @Transactional
    public void reset() {

        denominationRepository.deleteAllInBatch();

        denominationRepository.saveAll(createDenominations());
    }

    private List<Denomination> createDenominations() {

        return List.of(
                new Denomination(null, DenominationType.BANKNOTE,
                        new BigDecimal("1000.00"), 2),

                new Denomination(null, DenominationType.BANKNOTE,
                        new BigDecimal("500.00"), 5),

                new Denomination(null, DenominationType.BANKNOTE,
                        new BigDecimal("200.00"), 10),

                new Denomination(null, DenominationType.BANKNOTE,
                        new BigDecimal("100.00"), 20),

                new Denomination(null, DenominationType.BANKNOTE,
                        new BigDecimal("50.00"), 30),

                new Denomination(null, DenominationType.BANKNOTE,
                        new BigDecimal("20.00"), 40),

                new Denomination(null, DenominationType.COIN,
                        new BigDecimal("10.00"), 50),

                new Denomination(null, DenominationType.COIN,
                        new BigDecimal("5.00"), 100),

                new Denomination(null, DenominationType.COIN,
                        new BigDecimal("2.00"), 200),

                new Denomination(null, DenominationType.COIN,
                        new BigDecimal("1.00"), 300),

                new Denomination(null, DenominationType.COIN,
                        new BigDecimal("0.50"), 100)
        );
    }

    @Transactional(readOnly = true)
    public List<DenominationStock> getDenominations() {
        return denominationRepository.findAllByOrderByValueDesc().stream()
                .map(d -> new DenominationStock(
                        d.getDenominationType(),
                        d.getValue(),
                        d.getQuantity()
                ))
                .toList();
    }
}
