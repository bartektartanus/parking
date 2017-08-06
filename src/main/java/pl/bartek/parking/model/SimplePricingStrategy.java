package pl.bartek.parking.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SimplePricingStrategy implements PricingStrategy {

    private final Money firstHour;
    private final Money secondHour;
    private final double nextHourMultiplyFactor;

    public SimplePricingStrategy(Money firstHour, Money secondHour, double nextHourMultiplyFactor) {
        this.firstHour = firstHour;
        this.secondHour = secondHour;
        this.nextHourMultiplyFactor = nextHourMultiplyFactor;
    }

    @Override
    public Money calculate(LocalDateTime start, LocalDateTime end) {
        // you always have to pay at least for first hour
        long hours = (ChronoUnit.SECONDS.between(start, end) + 3600 - 1)/3600;
        Money sum = firstHour;
        Money nextHourRate = secondHour;
        for(int i = 2; i <= hours; i++){
            sum = sum.add(nextHourRate);
            nextHourRate = nextHourRate.multiply(nextHourMultiplyFactor);
        }
        return sum;
    }
}
