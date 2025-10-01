package me.m41k0n.investment.application.usecase;

import me.m41k0n.investment.application.usecase.command.UpdateInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.OperationType;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.exceptions.InvestmentNotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateInvestmentUseCaseTest {

    private final InvestmentRepository repository = mock(InvestmentRepository.class);
    private final UpdateInvestmentUseCase useCase = new UpdateInvestmentUseCase(repository);

    @Test
    void shouldUpdateInvestmentSuccessfully() {
        String id = UUID.randomUUID().toString();
        Investment original = new Investment(
                id, "Old Name", "Renda Fixa",
                new InvestmentValue(BigDecimal.valueOf(100.0)),
                LocalDate.now().minusDays(1),
                "Old Broker",
                new PurchaseRate(BigDecimal.valueOf(1.0)),
                new OperationType("COMPRA")
        );
        UpdateInvestmentCommand command = new UpdateInvestmentCommand(
                id, "New Name", "Renda Variável",
                BigDecimal.valueOf(200.0), LocalDate.now(), "New Broker", BigDecimal.valueOf(2.0), "VENDA"
        );

        when(repository.findById(id)).thenReturn(Optional.of(original));
        when(repository.save(any(Investment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Investment updated = useCase.execute(command);

        assertEquals("New Name", updated.name());
        assertEquals("Renda Variável", updated.type());
        assertEquals(BigDecimal.valueOf(200.0), updated.investmentValue().amount());
        assertEquals("New Broker", updated.broker());
        assertEquals(BigDecimal.valueOf(2.0), updated.purchaseRate().value());
        assertEquals("VENDA", updated.operationType().value());
    }

    @Test
    void shouldThrowExceptionIfInvestmentNotFound() {
        String id = UUID.randomUUID().toString();
        UpdateInvestmentCommand command = new UpdateInvestmentCommand(
                id, "Name", "Renda Fixa",
                BigDecimal.valueOf(100.0), LocalDate.now(), "Broker", BigDecimal.valueOf(1.0), "COMPRA"
        );
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InvestmentNotFoundException.class, () -> useCase.execute(command));
    }
}