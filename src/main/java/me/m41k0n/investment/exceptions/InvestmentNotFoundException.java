package me.m41k0n.investment.exceptions;

public class InvestmentNotFoundException extends BusinessException {
    public InvestmentNotFoundException(String id) {
        super("Investment not found: " + id);
    }
}