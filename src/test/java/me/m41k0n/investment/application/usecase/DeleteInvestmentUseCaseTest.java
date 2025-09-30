package me.m41k0n.investment.application.usecase;

import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.exceptions.InvestmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeleteInvestmentUseCaseTest {

    private InvestmentRepository investmentRepository;
    private DeleteInvestmentUseCase useCase;

    @BeforeEach
    void setUp() {
        investmentRepository = mock(InvestmentRepository.class);
        useCase = new DeleteInvestmentUseCase(investmentRepository);
    }

    @Test
    void testDeleteInvestmentSuccessfully() {
        String id = "existing-id";
        Investment investment = mock(Investment.class);

        when(investmentRepository.findById(id)).thenReturn(Optional.of(investment));
        doNothing().when(investmentRepository).deleteById(id);

        assertDoesNotThrow(() -> useCase.execute(id));
        verify(investmentRepository, times(1)).findById(id);
        verify(investmentRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteInvestmentThrowsIfNotFound() {
        String id = "non-existent-id";
        when(investmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InvestmentNotFoundException.class, () -> useCase.execute(id));
        verify(investmentRepository, times(1)).findById(id);
        verify(investmentRepository, never()).deleteById(any());
    }
}