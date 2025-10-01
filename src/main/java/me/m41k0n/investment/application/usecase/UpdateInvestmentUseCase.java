package me.m41k0n.investment.application.usecase;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.usecase.command.UpdateInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.OperationType;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.exceptions.InvestmentNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInvestmentUseCase {

    private final InvestmentRepository investmentRepository;

    public Investment execute(UpdateInvestmentCommand command) {
        Investment investment = investmentRepository.findById(command.id())
                .orElseThrow(() -> new InvestmentNotFoundException(command.id()));

        investment = investment.withUpdatedFields(
                command.name(),
                command.type(),
                new InvestmentValue(command.investmentValue()),
                command.purchaseDate(),
                command.broker(),
                new PurchaseRate(command.purchaseRate()),
                new OperationType(command.operationType())
        );

        return investmentRepository.save(investment);
    }
}