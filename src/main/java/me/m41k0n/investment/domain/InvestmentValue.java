package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;

import java.math.BigDecimal;

public record InvestmentValue(BigDecimal amount) {
    public InvestmentValue {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Investment value must be positive.");
    }
}
