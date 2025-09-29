package me.m41k0n.investment.application.usecase.command;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateInvestmentCommand(
        String id,
        String name,
        String type,
        BigDecimal investmentValue,
        LocalDate purchaseDate,
        String broker,
        BigDecimal purchaseRate
) {}