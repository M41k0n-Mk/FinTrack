package me.m41k0n.investment.application.usecase;

import me.m41k0n.investment.application.usecase.command.RegisterInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterInvestmentUseCaseTest {

    @Mock
    private InvestmentRepository investmentRepository;

    @InjectMocks
    private RegisterInvestmentUseCase registerInvestmentUseCase;

    @Test
    void shouldRegisterNewInvestmentSuccessfully() {
        RegisterInvestmentCommand command = new RegisterInvestmentCommand(
                "Ações XYZ",
                "Renda Variável",
                BigDecimal.valueOf(1000.0),
                LocalDate.now(),
                "XP Investimentos",
                BigDecimal.valueOf(10.50)
        );

        Investment expectedInvestment = Investment.createNew(
                command.name(),
                command.type(),
                new InvestmentValue(command.investmentValue()),
                command.purchaseDate(),
                command.broker(),
                new PurchaseRate(command.purchaseRate())
        );
        when(investmentRepository.save(any(Investment.class))).thenReturn(expectedInvestment);

        Investment result = registerInvestmentUseCase.execute(command);

        assertNotNull(result.id());
        assertEquals("Ações XYZ", result.name());
        assertEquals("Renda Variável", result.type());
        verify(investmentRepository, times(1)).save(any(Investment.class));
    }

    @Test
    void shouldThrowBusinessExceptionForInvalidType() {
        RegisterInvestmentCommand command = new RegisterInvestmentCommand(
                "Ações XYZ",
                "Tipo Inválido",
                BigDecimal.valueOf(1000.0),
                LocalDate.now(),
                "XP Investimentos",
                BigDecimal.valueOf(10.50)
        );

        assertThrows(BusinessException.class, () ->
                registerInvestmentUseCase.execute(command));
        verifyNoInteractions(investmentRepository);
    }

    @Test
    void shouldThrowBusinessExceptionForNegativeValue() {
        RegisterInvestmentCommand command = new RegisterInvestmentCommand(
                "Ações XYZ",
                "Renda Variável",
                BigDecimal.valueOf(-100.0),
                LocalDate.now(),
                "XP Investimentos",
                BigDecimal.valueOf(10.50)
        );

        assertThrows(BusinessException.class, () ->
                registerInvestmentUseCase.execute(command));
        verifyNoInteractions(investmentRepository);
    }
}