package com.atm.system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawRequest(
        @NotNull(message = "Amount is required")
        @DecimalMin(
                value = "0.00",
                inclusive = false,
                message = "Amount must be greater than zero"
        )
        BigDecimal amount
) {}
