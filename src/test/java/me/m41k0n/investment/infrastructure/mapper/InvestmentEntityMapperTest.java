package me.m41k0n.investment.infrastructure.mapper;

import me.m41k0n.investment.domain.*;
import me.m41k0n.investment.infrastructure.repository.InvestmentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvestmentEntityMapperTest {

    @Autowired
    private InvestmentEntityMapper mapper;

    @Test
    void shouldMapDomainToEntity() {
        Investment domain = new Investment(
                UUID.randomUUID().toString(),
                "Ações GHI",
                "Renda Variável",
                new InvestmentValue(BigDecimal.valueOf(250.0)),
                LocalDate.now(),
                "XP",
                new PurchaseRate(BigDecimal.valueOf(3.5))
        );

        InvestmentEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.id(), entity.getId());
        assertEquals(domain.name(), entity.getName());
        assertEquals(domain.type(), entity.getType());
        assertEquals(domain.investmentValue().amount(), entity.getInvestmentValue());
        assertEquals(domain.purchaseDate(), entity.getPurchaseDate());
        assertEquals(domain.broker(), entity.getBroker());
        assertEquals(domain.purchaseRate().value(), entity.getPurchaseRate());
    }

    @Test
    void shouldMapEntityToDomain() {
        InvestmentEntity entity = new InvestmentEntity();
        entity.setId(String.valueOf(UUID.randomUUID()));
        entity.setName("Ações JKL");
        entity.setType("Renda Variável");
        entity.setInvestmentValue(BigDecimal.valueOf(300.0));
        entity.setPurchaseDate(LocalDate.now());
        entity.setBroker("XP");
        entity.setPurchaseRate(BigDecimal.valueOf(4.0));

        Investment domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.id());
        assertEquals(entity.getName(), domain.name());
        assertEquals(entity.getType(), domain.type());
        assertEquals(entity.getInvestmentValue(), domain.investmentValue().amount());
        assertEquals(entity.getPurchaseDate(), domain.purchaseDate());
        assertEquals(entity.getBroker(), domain.broker());
        assertEquals(entity.getPurchaseRate(), domain.purchaseRate().value());
    }
}