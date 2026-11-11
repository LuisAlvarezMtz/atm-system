package com.atm.system.dto;

import com.atm.system.model.Denomination;

public record WithdrawalPlan(
        Denomination denomination,
        Integer quantity
) {}
