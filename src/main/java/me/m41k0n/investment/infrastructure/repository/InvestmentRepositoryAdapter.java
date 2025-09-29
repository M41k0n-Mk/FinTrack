package me.m41k0n.investment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.m41k0n.investment.domain.Investment;
import me.m41k0n.investment.domain.InvestmentRepository;
import me.m41k0n.investment.infrastructure.mapper.InvestmentEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Investment> findById(String id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Investment> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}