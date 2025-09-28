package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentValueTest {

    @Test
    void shouldCreateInvestmentValueWithPositiveAmount() {
        InvestmentValue value = new InvestmentValue(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), value.amount());
    }

    @Test
    void shouldThrowBusinessExceptionForNegativeAmount() {
        assertThrows(BusinessException.class, () -> new InvestmentValue(BigDecimal.valueOf(-1)));
    }

    @Test
    void shouldThrowBusinessExceptionForZeroAmount() {
        assertThrows(BusinessException.class, () -> new InvestmentValue(BigDecimal.ZERO));
    }
}