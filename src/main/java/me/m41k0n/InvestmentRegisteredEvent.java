package me.m41k0n;

public class InvestmentRegisteredEvent {
    private final String investmentId;
    // outros dados...

    public InvestmentRegisteredEvent(String investmentId) {
        this.investmentId = investmentId;
    }
}
