package com.atm.system.controller;

import com.atm.system.dto.DenominationDetail;
import com.atm.system.dto.DenominationStock;
import com.atm.system.dto.WithdrawRequest;
import com.atm.system.service.CashDispenserService;
import com.atm.system.service.DenominationManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/atm")
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

    @GetMapping("/denominations")
    public ResponseEntity<List<DenominationStock>> getDenominations() {
        return ResponseEntity.ok(managementService.getDenominations());
    }

    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reset() {
        managementService.reset();
    }

}
