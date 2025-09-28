package me.m41k0n.investment.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.usecase.RegisterInvestmentUseCase;
import me.m41k0n.investment.application.usecase.command.RegisterInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import me.m41k0n.investment.presentation.mapper.InvestmentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final RegisterInvestmentUseCase registerInvestmentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvestmentResponse register(@Valid @RequestBody InvestmentRequest request) {
        RegisterInvestmentCommand command = new RegisterInvestmentCommand(
                request.name(),
                request.type(),
                request.investmentValue(),
                request.purchaseDate(),
                request.broker(),
                request.purchaseRate()
        );
        Investment investment = registerInvestmentUseCase.execute(command);
        return InvestmentMapper.INSTANCE.toResponse(investment);
    }
}