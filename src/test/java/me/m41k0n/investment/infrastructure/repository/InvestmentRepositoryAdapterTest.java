package me.m41k0n.investment.infrastructure.repository;

import me.m41k0n.investment.domain.*;
import me.m41k0n.investment.infrastructure.mapper.InvestmentEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestmentRepositoryAdapterTest {

    @Mock
    private InvestmentJpaRepository investmentJpaRepository;

    @Mock
    private InvestmentEntityMapper investmentEntityMapper;

    @InjectMocks
    private InvestmentRepositoryAdapter investmentRepositoryAdapter;

    private Investment investment;
    private InvestmentEntity investmentEntity;

    @BeforeEach
    void setUp() {
        investment = new Investment(
                UUID.randomUUID().toString(),
                "Ações XYZ",
                "Renda Variável",
                new InvestmentValue(BigDecimal.valueOf(1000.0)),
                LocalDate.now(),
                "XP Investimentos",
                new PurchaseRate(BigDecimal.valueOf(10.50))
        );
        investmentEntity = new InvestmentEntity();
        investmentEntity.setId(investment.id());
        investmentEntity.setName(investment.name());
    }

    @Test
    void shouldSaveInvestmentSuccessfully() {
        when(investmentEntityMapper.toEntity(any(Investment.class))).thenReturn(investmentEntity);
        when(investmentJpaRepository.save(any(InvestmentEntity.class))).thenReturn(investmentEntity);
        when(investmentEntityMapper.toDomain(any(InvestmentEntity.class))).thenReturn(investment);

        Investment savedInvestment = investmentRepositoryAdapter.save(investment);

        assertNotNull(savedInvestment);
        verify(investmentJpaRepository, times(1)).save(any(InvestmentEntity.class));
        verify(investmentEntityMapper, times(1)).toDomain(any(InvestmentEntity.class));
        verify(investmentEntityMapper, times(1)).toEntity(any(Investment.class));
    }
}