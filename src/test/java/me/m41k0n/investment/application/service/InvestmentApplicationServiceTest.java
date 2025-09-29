package me.m41k0n.investment.application.service;

import me.m41k0n.investment.application.usecase.RegisterInvestmentUseCase;
import me.m41k0n.investment.application.usecase.UpdateInvestmentUseCase;
import me.m41k0n.investment.application.usecase.command.RegisterInvestmentCommand;
import me.m41k0n.investment.application.usecase.command.UpdateInvestmentCommand;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import me.m41k0n.investment.presentation.dto.InvestmentUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InvestmentApplicationServiceTest {

    private RegisterInvestmentUseCase registerInvestmentUseCase;
    private UpdateInvestmentUseCase updateInvestmentUseCase;
    private InvestmentRepository investmentRepository;
    private InvestmentApplicationService service;

    @BeforeEach
    void setUp() {
        registerInvestmentUseCase = mock(RegisterInvestmentUseCase.class);
        updateInvestmentUseCase = mock(UpdateInvestmentUseCase.class);
        investmentRepository = mock(InvestmentRepository.class);
        service = new InvestmentApplicationService(registerInvestmentUseCase, updateInvestmentUseCase, investmentRepository);
    }

    @Test
    void testRegisterInvestment() {
        InvestmentRequest request = new InvestmentRequest(
                "Tesouro Selic", "Tesouro Direto",
                BigDecimal.valueOf(1000.00), LocalDate.now(),
                "Banco do Brasil", BigDecimal.valueOf(0.00)
        );
        Investment mockInvestment = Investment.createNew(
                request.name(),
                request.type(),
                new me.m41k0n.investment.domain.InvestmentValue(request.investmentValue()),
                request.purchaseDate(),
                request.broker(),
                new me.m41k0n.investment.domain.PurchaseRate(request.purchaseRate())
        );

        when(registerInvestmentUseCase.execute(any(RegisterInvestmentCommand.class))).thenReturn(mockInvestment);

        InvestmentResponse response = service.registerInvestment(request);

        assertEquals(request.name(), response.name());
        assertEquals(request.type(), response.type());
        assertEquals(request.investmentValue(), response.investmentValue());
        assertEquals(request.purchaseDate(), response.purchaseDate());
        assertEquals(request.broker(), response.broker());
        assertEquals(request.purchaseRate(), response.purchaseRate());

        verify(registerInvestmentUseCase, times(1)).execute(any(RegisterInvestmentCommand.class));
    }

    @Test
    void testUpdateInvestment() {
        InvestmentUpdateRequest request = new InvestmentUpdateRequest(
                "id-1", "Tesouro Selic", "Tesouro Direto",
                BigDecimal.valueOf(1000.00), LocalDate.now(),
                "Banco do Brasil", BigDecimal.valueOf(0.00)
        );
        Investment mockInvestment = Investment.createNew(
                request.name(),
                request.type(),
                new me.m41k0n.investment.domain.InvestmentValue(request.investmentValue()),
                request.purchaseDate(),
                request.broker(),
                new me.m41k0n.investment.domain.PurchaseRate(request.purchaseRate())
        );

        when(updateInvestmentUseCase.execute(any(UpdateInvestmentCommand.class))).thenReturn(mockInvestment);

        InvestmentResponse response = service.updateInvestment(request);

        assertEquals(request.name(), response.name());
        assertEquals(request.type(), response.type());
        assertEquals(request.investmentValue(), response.investmentValue());
        assertEquals(request.purchaseDate(), response.purchaseDate());
        assertEquals(request.broker(), response.broker());
        assertEquals(request.purchaseRate(), response.purchaseRate());

        verify(updateInvestmentUseCase, times(1)).execute(any(UpdateInvestmentCommand.class));
    }

    @Test
    void testFindAllReturnsListOfResponses() {
        Investment investment1 = Investment.createNew(
                "Investimento 1",
                "Renda Fixa",
                new InvestmentValue(BigDecimal.valueOf(100)),
                LocalDate.of(2022, 1, 1),
                "Corretora 1",
                new PurchaseRate(BigDecimal.valueOf(1))
        );
        Investment investment2 = Investment.createNew(
                "Investimento 2",
                "Renda Variável",
                new InvestmentValue(BigDecimal.valueOf(200)),
                LocalDate.of(2022, 2, 2),
                "Corretora 2",
                new PurchaseRate(BigDecimal.valueOf(2))
        );

        when(investmentRepository.findAll()).thenReturn(Arrays.asList(investment1, investment2));

        List<InvestmentResponse> responses = service.findAll();

        assertEquals(2, responses.size());
        assertEquals("Investimento 1", responses.get(0).name());
        assertEquals("Investimento 2", responses.get(1).name());
        verify(investmentRepository, times(1)).findAll();
    }

    @Test
    void testFindAllReturnsEmptyList() {
        when(investmentRepository.findAll()).thenReturn(Collections.emptyList());

        List<InvestmentResponse> responses = service.findAll();

        assertTrue(responses.isEmpty());
        verify(investmentRepository, times(1)).findAll();
    }
}