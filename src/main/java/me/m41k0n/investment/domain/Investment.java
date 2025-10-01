package me.m41k0n.investment.domain;

import me.m41k0n.investment.exceptions.BusinessException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Investment(
        String id,
        String name,
        String type,
        InvestmentValue investmentValue,
        LocalDate purchaseDate,
        String broker,
        PurchaseRate purchaseRate,
        OperationType operationType
) {
    public Investment {
        if (name == null || name.isBlank())
            throw new BusinessException("Name is required.");
        if (type == null || !allowedTypes().contains(type))
            throw new BusinessException("Invalid investment type.");
        if (broker == null || broker.isBlank())
            throw new BusinessException("Broker is required.");
        if (purchaseDate == null)
            throw new BusinessException("Purchase date is required.");
        if (purchaseDate.isAfter(LocalDate.now()))
            throw new BusinessException("Purchase date cannot be in the future.");
        if (investmentValue == null)
            throw new BusinessException("Investment value is required.");
        if (purchaseRate == null)
            throw new BusinessException("Purchase rate is required.");
        if (operationType == null)
            throw new BusinessException("Operation type is required.");
    }

    public static Investment createNew(
            String name,
            String type,
            InvestmentValue investmentValue,
            LocalDate purchaseDate,
            String broker,
            PurchaseRate purchaseRate,
            OperationType operationType
    ) {
        return new Investment(
                UUID.randomUUID().toString(),
                name,
                type,
                investmentValue,
                purchaseDate,
                broker,
                purchaseRate,
                operationType
        );
    }

    private static List<String> allowedTypes() {
        return List.of(
                "Renda Fixa", "Renda Variável", "Tesouro Direto", "Fundos",
                "Previdência", "Criptoativos", "Imóveis", "Outros"
        );
    }

    public Investment withUpdatedFields(
            String name,
            String type,
            InvestmentValue investmentValue,
            LocalDate purchaseDate,
            String broker,
            PurchaseRate purchaseRate,
            OperationType operationType
    ) {
        return new Investment(
                this.id,
                name,
                type,
                investmentValue,
                purchaseDate,
                broker,
                purchaseRate,
                operationType
        );
    }
}