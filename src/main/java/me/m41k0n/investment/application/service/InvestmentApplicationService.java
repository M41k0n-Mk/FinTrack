package me.m41k0n.investment.application.service;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.usecase.RegisterInvestmentUseCase;
import me.m41k0n.investment.application.usecase.UpdateInvestmentUseCase;
import me.m41k0n.investment.application.usecase.DeleteInvestmentUseCase;
import me.m41k0n.investment.application.usecase.command.RegisterInvestmentCommand;
import me.m41k0n.investment.application.usecase.command.UpdateInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import me.m41k0n.investment.presentation.dto.InvestmentUpdateRequest;
import me.m41k0n.investment.presentation.mapper.InvestmentMapper;
import me.m41k0n.investment.presentation.mapper.InvestmentUpdateMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentApplicationService {

    private final RegisterInvestmentUseCase registerInvestmentUseCase;
    private final UpdateInvestmentUseCase updateInvestmentUseCase;
    private final DeleteInvestmentUseCase deleteInvestmentUseCase;
    private final InvestmentRepository investmentRepository;

    public InvestmentResponse registerInvestment(InvestmentRequest request) {
        RegisterInvestmentCommand command = new RegisterInvestmentCommand(
                request.name(),
                request.type(),
                request.investmentValue(),
                request.purchaseDate(),
                request.broker(),
                request.purchaseRate(),
                request.operationType()
        );
        Investment investment = registerInvestmentUseCase.execute(command);
        return InvestmentMapper.INSTANCE.toResponse(investment);
    }

    public InvestmentResponse updateInvestment(InvestmentUpdateRequest request) {
        UpdateInvestmentCommand command = InvestmentUpdateMapper.INSTANCE.toCommand(request);
        Investment investment = updateInvestmentUseCase.execute(command);
        return InvestmentMapper.INSTANCE.toResponse(investment);
    }

    public List<InvestmentResponse> findAll() {
        List<Investment> investments = investmentRepository.findAll();
        return investments.stream()
                .map(InvestmentMapper.INSTANCE::toResponse)
                .toList();
    }

    public void deleteInvestment(String id) {
        deleteInvestmentUseCase.execute(id);
    }
}