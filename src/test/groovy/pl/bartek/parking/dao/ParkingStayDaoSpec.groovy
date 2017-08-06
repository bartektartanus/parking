package pl.bartek.parking.dao

import pl.bartek.parking.ClockUtils
import pl.bartek.parking.model.ParkingStay
import pl.bartek.parking.model.ParkingStayFactory
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDate


class ParkingStayDaoSpec extends Specification {

    def clock1 = ClockUtils.fixedClock(LocalDate.of(2016,1,1))
    def clock2 = ClockUtils.fixedClock(LocalDate.of(2015,1,1))
    def parkingStayDao = ParkingStayDaoImpl.instance

    def shouldSaveAndFindByNumber(){
        given:
        def stay = ParkingStayFactory.regular("1")
        stay.start(clock1)
        when:
        parkingStayDao.save(stay)
        then:
        parkingStayDao.findByVehicleNumberAndNotEnded("1")
    }

    def shouldSaveAndFindByEndDate(){
        given:
        def stay1 = ParkingStayFactory.regular("1")
        stay1.start(clock2)
        def stay2 = ParkingStayFactory.regular("2")
        stay2.start(clock2)
        stay2.end(clock2)
        when:
        parkingStayDao.save(stay1)
        parkingStayDao.save(stay2)
        then:
        parkingStayDao.findAllByEndDate(LocalDate.of(2015,1,1)).size() == 1
        parkingStayDao.findByVehicleNumberAndNotEnded("1").isDefined()
    }

}