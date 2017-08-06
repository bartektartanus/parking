import pl.bartek.parking.model.ParkingStay
import spock.lang.Specification


class ParkingStaySpec extends Specification {


    def shouldBeStarted(){
        given:
        def stay = ParkingStay.regular("AB12345")
        expect:
        !stay.isStarted()
        when:
        stay.start()
        then:
        stay.isStarted()
    }

    def shouldBeEnded(){
        given:
        def stay = ParkingStay.regular("AB12345")
        expect:
        !stay.isEnded()
        when:
        stay.start()
        stay.end()
        then:
        stay.isEnded()

    }
}