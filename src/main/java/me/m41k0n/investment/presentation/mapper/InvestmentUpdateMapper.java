package me.m41k0n.investment.presentation.mapper;

import me.m41k0n.investment.application.usecase.command.UpdateInvestmentCommand;
import me.m41k0n.investment.presentation.dto.InvestmentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvestmentUpdateMapper {
    InvestmentUpdateMapper INSTANCE = Mappers.getMapper(InvestmentUpdateMapper.class);

    UpdateInvestmentCommand toCommand(InvestmentUpdateRequest request);
}