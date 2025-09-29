package me.m41k0n.investment.infrastructure.repository;

import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.infrastructure.mapper.InvestmentEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void shouldFindInvestmentByIdSuccessfully() {
        String id = investment.id();
        when(investmentJpaRepository.findById(id)).thenReturn(Optional.of(investmentEntity));
        when(investmentEntityMapper.toDomain(investmentEntity)).thenReturn(investment);

        Optional<Investment> result = investmentRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(investment, result.get());
        verify(investmentJpaRepository, times(1)).findById(id);
        verify(investmentEntityMapper, times(1)).toDomain(investmentEntity);
    }

    @Test
    void shouldReturnEmptyWhenInvestmentNotFoundById() {
        String id = "non-existing-id";
        when(investmentJpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Investment> result = investmentRepositoryAdapter.findById(id);

        assertTrue(result.isEmpty());
        verify(investmentJpaRepository, times(1)).findById(id);
        verify(investmentEntityMapper, never()).toDomain(any());
    }

    @Test
    void shouldFindAllInvestmentsSuccessfully() {
        List<InvestmentEntity> entities = Collections.singletonList(investmentEntity);
        when(investmentJpaRepository.findAll()).thenReturn(entities);
        when(investmentEntityMapper.toDomain(investmentEntity)).thenReturn(investment);

        List<Investment> result = investmentRepositoryAdapter.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(investment, result.get(0));
        verify(investmentJpaRepository, times(1)).findAll();
        verify(investmentEntityMapper, times(1)).toDomain(investmentEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoInvestmentsFound() {
        when(investmentJpaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Investment> result = investmentRepositoryAdapter.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(investmentJpaRepository, times(1)).findAll();
        verify(investmentEntityMapper, never()).toDomain(any());
    }
}