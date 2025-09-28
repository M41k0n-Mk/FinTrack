package me.m41k0n.investment.application.service;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.usecase.RegisterInvestmentUseCase;
import me.m41k0n.investment.application.usecase.command.RegisterInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import me.m41k0n.investment.presentation.mapper.InvestmentMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestmentApplicationService {

    private final RegisterInvestmentUseCase registerInvestmentUseCase;

    public InvestmentResponse registerInvestment(InvestmentRequest request) {
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