package me.m41k0n.investment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.infrastructure.mapper.InvestmentEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InvestmentRepositoryAdapter implements InvestmentRepository {

    private final InvestmentJpaRepository jpaRepository;
    private final InvestmentEntityMapper mapper;

    @Override
    public Investment save(Investment investment) {
        InvestmentEntity entity = mapper.toEntity(investment);
        InvestmentEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}