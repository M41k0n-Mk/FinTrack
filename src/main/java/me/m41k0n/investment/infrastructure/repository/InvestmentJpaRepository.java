package me.m41k0n.investment.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentJpaRepository extends JpaRepository<InvestmentEntity, String> {}