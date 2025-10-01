package me.m41k0n.investment.infrastructure.mapper;

import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.OperationType;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.infrastructure.repository.InvestmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface InvestmentEntityMapper {

    @Mapping(target = "investmentValue", source = "investmentValue", qualifiedByName = "toBigDecimal")
    @Mapping(target = "purchaseRate", source = "purchaseRate", qualifiedByName = "toBigDecimalRate")
    @Mapping(target = "operationType", source = "operationType", qualifiedByName = "toString")
    InvestmentEntity toEntity(Investment domain);

    @Mapping(target = "investmentValue", source = "investmentValue", qualifiedByName = "toInvestmentValue")
    @Mapping(target = "purchaseRate", source = "purchaseRate", qualifiedByName = "toPurchaseRate")
    @Mapping(target = "operationType", source = "operationType", qualifiedByName = "toOperationType")
    Investment toDomain(InvestmentEntity entity);

    @Named("toBigDecimal")
    static BigDecimal toBigDecimal(InvestmentValue value) {
        return value == null ? null : value.amount();
    }

    @Named("toBigDecimalRate")
    static BigDecimal toBigDecimalRate(PurchaseRate rate) {
        return rate == null ? null : rate.value();
    }

    @Named("toInvestmentValue")
    static InvestmentValue toInvestmentValue(BigDecimal value) {
        return value == null ? null : new InvestmentValue(value);
    }

    @Named("toPurchaseRate")
    static PurchaseRate toPurchaseRate(BigDecimal value) {
        return value == null ? null : new PurchaseRate(value);
    }

    @Named("toString")
    static String toString(OperationType operationType) {
        return operationType == null ? null : operationType.value();
    }

    @Named("toOperationType")
    static OperationType toOperationType(String value) {
        return value == null ? null : new OperationType(value);
    }
}