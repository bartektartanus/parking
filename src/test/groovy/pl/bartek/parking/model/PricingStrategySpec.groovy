package pl.bartek.parking.model

import pl.bartek.parking.model.Money
import pl.bartek.parking.model.PricingStrategy
import pl.bartek.parking.model.SimplePricingStrategy
import spock.lang.Specification

import java.time.LocalDateTime


class PricingStrategySpec extends Specification {

    def shouldCalculateValidCharge(){
        given:
        def strategy = new SimplePricingStrategy(Money.fromPlnZlote(1), Money.fromPlnZlote(2), 2)
        expect:
        calculateHours(strategy, a) == Money.fromPlnZlote(b)
        where:
        a | b
        1 | 1
        2 | 3
        3 | 7
        4 | 15
        5 | 31

    }

    def shouldCalculateValidChargeWithConstantHourRate(){
        given:
        def strategy = new SimplePricingStrategy(Money.fromPlnZlote(1), Money.fromPlnZlote(1), 1)
        expect:
        calculateHours(strategy, a) == Money.fromPlnZlote(b)
        where:
        a | b
        1 | 1
        2 | 2
        3 | 3
        4 | 4
        5 | 5

    }

    def shouldCalculateValidChargeWithFirstHourFreeOfCharge(){
        given:
        def strategy = new SimplePricingStrategy(Money.fromPlnZlote(0), Money.fromPlnZlote(3), 2)
        expect:
        calculateHours(strategy, a) == Money.fromPlnZlote(b)
        where:
        a | b
        1 | 0
        2 | 3
        3 | 9
        4 | 21
        5 | 45

    }

    def shouldCalculateValidChargeWithPartialMultiplyFactor(){
        given:
        def strategy = new SimplePricingStrategy(Money.fromPlnZlote(1), Money.fromPlnZlote(4), 1.5)
        expect:
        calculateHours(strategy, a) == Money.fromPlnGrosze(b)
        where:
        a | b
        1 | 100
        2 | 500
        3 | 1100
        4 | 2000
        5 | 3350

    }

    def shouldCalculateValidChargeForMinutes(){
        given:
        def strategy = new SimplePricingStrategy(Money.fromPlnZlote(1), Money.fromPlnZlote(2), 2)
        expect:
        calculateMinutes(strategy, a) == Money.fromPlnZlote(b)
        where:
        a   | b
        1   | 1
        15  | 1
        30  | 1
        60  | 1
        61  | 3
        120 | 3
        121 | 7
    }

    private static Money calculateHours(PricingStrategy strategy, int hours){
        LocalDateTime date = LocalDateTime.now()
        return strategy.calculate(date, date.plusHours(hours))
    }


    private static Money calculateMinutes(PricingStrategy strategy, int minutes){
        LocalDateTime date = LocalDateTime.now()
        return strategy.calculate(date, date.plusMinutes(minutes))
    }
}