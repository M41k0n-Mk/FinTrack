package me.m41k0n.investment.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.service.InvestmentApplicationService;
import me.m41k0n.investment.exceptions.BadRequestException;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import me.m41k0n.investment.presentation.dto.InvestmentUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvestmentResponse update(@PathVariable String id, @Valid @RequestBody InvestmentUpdateRequest request) {
        if (!id.equals(request.id())) {
            throw new BadRequestException("Id in path and body must match.");
        }

        InvestmentUpdateRequest fixedRequest = new InvestmentUpdateRequest(
                id,
                request.name(),
                request.type(),
                request.investmentValue(),
                request.purchaseDate(),
                request.broker(),
                request.purchaseRate()
        );
        return investmentApplicationService.updateInvestment(fixedRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvestmentResponse> findAll() {
        return investmentApplicationService.findAll();
    }
}