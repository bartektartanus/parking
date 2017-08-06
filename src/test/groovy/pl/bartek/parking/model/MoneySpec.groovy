package pl.bartek.parking.model

import pl.bartek.parking.model.Money
import spock.lang.Specification


class MoneySpec extends Specification {

    def shouldAdd(){
        expect:
        Money.fromPlnGrosze(a).add(Money.fromPlnGrosze(b)) ==  Money.fromPlnGrosze(c)
        where:
        a   | b     | c
        10  | 20    | 30
        100 | 300   | 400
        10  | 500   | 510
        0   | 0     | 0
        0   | 234   | 234
    }

    def shouldMultiply(){
        given:
        def one = Money.fromPlnGrosze(100)
        when:
        def result = one.multiply(33)
        then:
        result == Money.fromPlnGrosze(3300)
    }

    def shouldCreateValidObject(){
        expect:
        Money.fromPlnGrosze(i * 100) == Money.fromPlnZlote(i)
        where:
        i << [0,1,2,11,33,999]
    }

    def shouldConvertToString(){
        expect:
        Money.fromPlnGrosze(a).toString() == b
        where:
        a       | b
        0       | "0.00 PLN"
        10      | "0.10 PLN"
        100     | "1.00 PLN"
        115     | "1.15 PLN"
        5000    | "50.00 PLN"
    }
}