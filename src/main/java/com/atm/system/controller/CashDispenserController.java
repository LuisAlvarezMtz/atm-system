package com.atm.system.controller;

import com.atm.system.dto.DenominationDetail;
import com.atm.system.dto.WithdrawRequest;
import com.atm.system.service.CashDispenserService;
import com.atm.system.service.DenominationManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/atm")
@RequiredArgsConstructor
public class CashDispenserController {

    private final CashDispenserService cashDispenserService;
    private final DenominationManagementService managementService;

    @PostMapping("/withdraw")
    public List<DenominationDetail> withdraw(
            @RequestBody WithdrawRequest request) {

        return cashDispenserService.withdraw(request.amount());
    }

    @PostMapping("/reset")
    public String reset() {
        managementService.reset();
        return "success";
    }

}
