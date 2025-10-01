package me.m41k0n.investment.presentation.controller.mapper;

import me.m41k0n.investment.domain.*;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import me.m41k0n.investment.presentation.mapper.InvestmentMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentMapperTest {

    private final InvestmentMapper mapper = InvestmentMapper.INSTANCE;

    @Test
    void shouldMapRequestToDomain() {
        InvestmentRequest request = new InvestmentRequest(
                "Ações ABC", "Renda Variável", BigDecimal.valueOf(150.0), LocalDate.now(), "XP", BigDecimal.valueOf(2.5), "COMPRA"
        );

        Investment domain = mapper.toDomain(request);

        assertNotNull(domain);
        assertNull(domain.id());
        assertEquals(request.name(), domain.name());
        assertEquals(request.type(), domain.type());
        assertEquals(request.investmentValue(), domain.investmentValue().amount());
        assertEquals(request.purchaseDate(), domain.purchaseDate());
        assertEquals(request.broker(), domain.broker());
        assertEquals(request.purchaseRate(), domain.purchaseRate().value());
        assertEquals(request.operationType(), domain.operationType().value());
    }

    @Test
    void shouldMapDomainToResponse() {
        Investment domain = new Investment(
                UUID.randomUUID().toString(),
                "Ações DEF", "Renda Variável", new InvestmentValue(BigDecimal.valueOf(200.0)), LocalDate.now(), "XP", new PurchaseRate(BigDecimal.valueOf(3.0)), new OperationType("COMPRA")
        );

        InvestmentResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(domain.id(), response.id());
        assertEquals(domain.name(), response.name());
        assertEquals(domain.type(), response.type());
        assertEquals(domain.investmentValue().amount(), response.investmentValue());
        assertEquals(domain.purchaseDate(), response.purchaseDate());
        assertEquals(domain.broker(), response.broker());
        assertEquals(domain.purchaseRate().value(), response.purchaseRate());
        assertEquals(domain.operationType().value(), response.operationType());
    }
}