package me.m41k0n.investment.presentation.mapper;

import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentValue;
import me.m41k0n.investment.domain.PurchaseRate;
import me.m41k0n.investment.presentation.dto.InvestmentRequest;
import me.m41k0n.investment.presentation.dto.InvestmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper
public interface InvestmentMapper {

    InvestmentMapper INSTANCE = Mappers.getMapper(InvestmentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "investmentValue", source = "investmentValue", qualifiedByName = "toInvestmentValue")
    @Mapping(target = "purchaseRate", source = "purchaseRate", qualifiedByName = "toPurchaseRate")
    Investment toDomain(InvestmentRequest request);

    @Mapping(target = "investmentValue", source = "investmentValue.amount")
    @Mapping(target = "purchaseRate", source = "purchaseRate.value")
    InvestmentResponse toResponse(Investment domain);

   @Named("toInvestmentValue")
    static InvestmentValue toInvestmentValue(BigDecimal value) {
        return value == null ? null : new InvestmentValue(value);
    }

    @Named("toPurchaseRate")
    static PurchaseRate toPurchaseRate(BigDecimal value) {
        return value == null ? null : new PurchaseRate(value);
    }
}