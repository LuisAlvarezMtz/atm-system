package com.atm.system.controller;

import com.atm.system.dto.DenominationDetail;
import com.atm.system.dto.WithdrawRequest;
import com.atm.system.service.CashDispenserService;
import com.atm.system.service.DenominationManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/atm")
@RequiredArgsConstructor
public class CashDispenserController {

    private final CashDispenserService cashDispenserService;
    private final DenominationManagementService managementService;

    @PostMapping("/withdraw")
    public ResponseEntity<List<DenominationDetail>> withdraw(
            @Valid @RequestBody WithdrawRequest request) {

        return ResponseEntity.ok(
                cashDispenserService.withdraw(request.amount())
        );
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {

        managementService.reset();

        return ResponseEntity.ok("atm reset successfully");
    }

}
