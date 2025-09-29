package me.m41k0n.investment.domain;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository {

    Investment save(Investment investment);

    Optional<Investment> findById(String id);

    List<Investment> findAll();
}