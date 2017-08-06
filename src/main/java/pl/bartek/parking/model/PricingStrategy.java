package pl.bartek.parking.model;

import java.time.LocalDateTime;

@FunctionalInterface
public interface PricingStrategy {

    Money calculate(LocalDateTime start, LocalDateTime end);

}
