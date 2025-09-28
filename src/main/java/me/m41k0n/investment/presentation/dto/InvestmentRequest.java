package me.m41k0n.investment.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestmentRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Type cannot be blank") String type,
        @DecimalMin(value = "0.01", message = "Value must be positive") BigDecimal investmentValue,
        @NotNull(message = "Purchase date cannot be null") LocalDate purchaseDate,
        @NotBlank(message = "Broker cannot be blank") String broker,
        @NotNull(message = "Purchase rate cannot be null") BigDecimal purchaseRate
) {}