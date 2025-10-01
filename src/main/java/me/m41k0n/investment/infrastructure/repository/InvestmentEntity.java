package me.m41k0n.investment.infrastructure.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "investments")
@Data
@NoArgsConstructor
public class InvestmentEntity {
    @Id
    private String id;
    private String name;
    private String type;
    private BigDecimal investmentValue;
    private LocalDate purchaseDate;
    private String broker;
    private BigDecimal purchaseRate;
    private String operationType;
}