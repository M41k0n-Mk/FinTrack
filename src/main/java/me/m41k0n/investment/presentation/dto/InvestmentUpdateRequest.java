package me.m41k0n.investment.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestmentUpdateRequest(
        @NotBlank(message = "Id cannot be blank") String id,
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Type cannot be blank") String type,
        @NotNull(message = "Value cannot be null") BigDecimal investmentValue,
        @NotNull(message = "Purchase date cannot be null") LocalDate purchaseDate,
        @NotBlank(message = "Broker cannot be blank") String broker,
        @NotNull(message = "Purchase rate cannot be null") BigDecimal purchaseRate,
        @NotBlank(message = "Operation type cannot be blank") String operationType
) {}
