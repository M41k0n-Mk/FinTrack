package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeTest {

    @Test
    void shouldCreateOperationTypeWithCompra() {
        OperationType operationType = new OperationType("COMPRA");
        assertEquals("COMPRA", operationType.value());
    }

    @Test
    void shouldCreateOperationTypeWithVenda() {
        OperationType operationType = new OperationType("VENDA");
        assertEquals("VENDA", operationType.value());
    }

    @Test
    void shouldThrowBusinessExceptionForNullValue() {
        assertThrows(BusinessException.class, () -> new OperationType(null));
    }

    @Test
    void shouldThrowBusinessExceptionForBlankValue() {
        assertThrows(BusinessException.class, () -> new OperationType(""));
    }

    @Test
    void shouldThrowBusinessExceptionForInvalidValue() {
        assertThrows(BusinessException.class, () -> new OperationType("INVALID"));
    }
}
