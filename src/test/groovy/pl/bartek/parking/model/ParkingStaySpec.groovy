package pl.bartek.parking.model

import pl.bartek.parking.model.ParkingStay
import spock.lang.Specification

import java.time.Clock


class ParkingStaySpec extends Specification {

    def clock = Clock.systemDefaultZone();

    def shouldBeStarted(){
        given:
        def stay = ParkingStayFactory.regular("AB12345")
        expect:
        !stay.isStarted()
        when:
        stay.start(clock)
        then:
        stay.isStarted()
    }

    def shouldBeEnded(){
        given:
        def stay = ParkingStayFactory.regular("AB12345")
        expect:
        !stay.isEnded()
        when:
        stay.start(clock)
        stay.end(clock)
        then:
        stay.isEnded()

    }
}