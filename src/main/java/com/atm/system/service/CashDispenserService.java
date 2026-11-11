package com.atm.system.service;

import com.atm.system.dto.DenominationDetail;
import com.atm.system.dto.WithdrawalPlan;
import com.atm.system.model.Denomination;
import com.atm.system.repository.DenominationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashDispenserService {

    private final DenominationRepository denominationRepository;

    @Transactional
    public List<DenominationDetail> withdraw(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        List<Denomination> denominations = denominationRepository
                .findDenominationsForUpdate();

        BigDecimal remainingAmount = amount;

        List<DenominationDetail> details = new ArrayList<>();

        List<WithdrawalPlan> plans = new ArrayList<>();


        for (Denomination denomination : denominations) {
            BigDecimal value = denomination.getValue();
            Integer quantity = denomination.getQuantity();

            if (quantity <= 0) {
                continue;
            }

            BigDecimal possibleQuantity = remainingAmount.divideToIntegralValue(value);

            int quantityToTake = Math.min(possibleQuantity.intValueExact(), quantity);

            if (quantityToTake == 0) {
                continue;
            }

            BigDecimal subtotal = value.multiply(
                    BigDecimal.valueOf(quantityToTake)
            );

            remainingAmount = remainingAmount.subtract(subtotal);

            plans.add(new WithdrawalPlan(
                    denomination,
                    quantityToTake
            ));

            details.add(new DenominationDetail(
                    denomination.getDenominationType(),
                    value,
                    quantityToTake,
                    subtotal
            ));

        }


        if (remainingAmount.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException
                    ("It's not possible to provide the requested amount using the available denominations");
        }

        for (WithdrawalPlan plan : plans) {

            Denomination denomination = plan.denomination();

            denomination.setQuantity(
                    denomination.getQuantity() - plan.quantity()
            );

        }

        return details;

    }
}
