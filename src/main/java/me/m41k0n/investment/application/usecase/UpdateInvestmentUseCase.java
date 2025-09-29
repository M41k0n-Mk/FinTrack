package me.m41k0n.investment.application.usecase;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.usecase.command.UpdateInvestmentCommand;
import me.m41k0n.investment.domain.*;
import me.m41k0n.investment.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInvestmentUseCase {

    private final InvestmentRepository investmentRepository;

    public Investment execute(UpdateInvestmentCommand command) {
        Investment investment = investmentRepository.findById(command.id())
                .orElseThrow(() -> new BusinessException("Investment not found"));

        investment = investment.withUpdatedFields(
                command.name(),
                command.type(),
                new InvestmentValue(command.investmentValue()),
                command.purchaseDate(),
                command.broker(),
                new PurchaseRate(command.purchaseRate())
        );

        return investmentRepository.save(investment);
    }
}