package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PurchaseRateTest {

    @Test
    void shouldCreatePurchaseRateWithNonNegativeValue() {
        PurchaseRate rate = new PurchaseRate(BigDecimal.valueOf(0.5));
        assertEquals(BigDecimal.valueOf(0.5), rate.value());
    }

    @Test
    void shouldThrowBusinessExceptionForNegativeValue() {
        assertThrows(BusinessException.class, () -> new PurchaseRate(BigDecimal.valueOf(-1)));
    }
}