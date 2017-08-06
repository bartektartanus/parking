package pl.bartek.parking.model;

public enum CustomerType {
    REGULAR(new SimplePricingStrategy(Money.fromPlnZlote(1), Money.fromPlnZlote(2), 2)),
    VIP(new SimplePricingStrategy(Money.fromPlnZlote(0), Money.fromPlnZlote(2), 1.5));

    private PricingStrategy strategy;

    CustomerType(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public PricingStrategy getStrategy() {
        return strategy;
    }
}
