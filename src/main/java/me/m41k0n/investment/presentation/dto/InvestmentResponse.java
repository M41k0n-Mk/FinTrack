package me.m41k0n.investment.presentation.dto;

import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestmentResponse(
        String id,
        String name,
        String type,
        BigDecimal value,
        LocalDate purchaseDate,
        String broker,
        BigDecimal purchaseRate
) {}