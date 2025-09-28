package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;

import java.math.BigDecimal;

public record PurchaseRate(BigDecimal value) {
    public PurchaseRate {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0)
            throw new BusinessException("Purchase rate must be non-negative.");
    }
}