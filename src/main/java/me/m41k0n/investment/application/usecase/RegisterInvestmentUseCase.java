package me.m41k0n.investment.application.usecase;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.application.usecase.command.RegisterInvestmentCommand;
import me.m41k0n.investment.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterInvestmentUseCase {
    private final InvestmentRepository repository;

    public Investment execute(RegisterInvestmentCommand command) {
        Investment investment = Investment.createNew(
                command.name(),
                command.type(),
                new InvestmentValue(command.investmentValue()),
                command.purchaseDate(),
                command.broker(),
                new PurchaseRate(command.purchaseRate()),
                new OperationType(command.operationType())
        );
        return repository.save(investment);
    }
}