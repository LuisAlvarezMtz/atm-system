package com.atm.system.dto;

import com.atm.system.model.DenominationType;

import java.math.BigDecimal;

public record DenominationStock(
        DenominationType denominationType,
        BigDecimal value,
        Integer quantity
) {}
