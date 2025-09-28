package me.m41k0n.investment.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.service.InvestmentApplicationService;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentApplicationService investmentApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvestmentResponse register(@Valid @RequestBody InvestmentRequest request) {
        return investmentApplicationService.registerInvestment(request);
    }
}