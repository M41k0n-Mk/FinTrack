package me.m41k0n.investment.application.usecase.command;

import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterInvestmentCommand(
        String name,
        String type,
        BigDecimal investmentValue,
        LocalDate purchaseDate,
        String broker,
        BigDecimal purchaseRate,
        String operationType
) {}