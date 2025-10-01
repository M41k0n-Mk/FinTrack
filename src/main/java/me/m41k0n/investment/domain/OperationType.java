package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;

public record OperationType(String value) {
    public OperationType {
        if (value == null || value.isBlank())
            throw new BusinessException("Operation type is required.");
        if (!value.equals("COMPRA") && !value.equals("VENDA"))
            throw new BusinessException("Operation type must be COMPRA or VENDA.");
    }
}
