package me.m41k0n.investment.application.usecase;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.exceptions.InvestmentNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInvestmentUseCase {

    private final InvestmentRepository investmentRepository;

    public void execute(String id) {
        boolean exists = investmentRepository.findById(id).isPresent();
        if (!exists) {
            throw new InvestmentNotFoundException(id);
        }
        investmentRepository.deleteById(id);
    }
}