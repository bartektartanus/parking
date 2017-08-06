package pl.bartek.parking

import pl.bartek.parking.model.Money
import pl.bartek.parking.service.DriverService
import pl.bartek.parking.service.ParkingOperatorService
import pl.bartek.parking.service.ParkingStayStartRequest
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset


class UserStoriesSpec extends Specification {

    def driverService = new DriverService()
    def operatorService = new ParkingOperatorService()


    // this tests two user stories - driver won't pay the fine if the operator knows that he started meter
    //"As a parking operator, I want to check if the vehicle has started the parking meter."
    def "As a driver, I want to start the parking meter, so I don’t have to pay the fine for the invalid parking."(){
        given:
        def vehicleRegistrationNumber = "AB123"
        def request = new ParkingStayStartRequest(vehicleRegistrationNumber, "regular")
        when:
        driverService.start(request)
        then:
        operatorService.isStarted(vehicleRegistrationNumber)

    }

    def "As a driver, I want to stop the parking meter, so that I pay only for the actual parking time"(){
        given:
        def vehicleRegistrationNumber = "AB12345"
        def request = new ParkingStayStartRequest(vehicleRegistrationNumber, "regular")
        when:
        driverService.start(request)
        then:
        operatorService.isStarted(vehicleRegistrationNumber)
        when:
        driverService.end(vehicleRegistrationNumber)
        then:
        !operatorService.isStarted(vehicleRegistrationNumber)
    }

    def "As a driver, I want to know how much I have to pay for parking."(){
        given:
        def vehicleRegistrationNumber = "XYZ123"
        def request = new ParkingStayStartRequest(vehicleRegistrationNumber, "regular")
        when:
        driverService.start(request)
        then:
        driverService.checkCharge(vehicleRegistrationNumber) == Money.fromPlnZlote(1)
    }

    def "As a parking owner, I want to know how much money was earned during a given day."(){
        given:
        def date = LocalDate.of(2010, 10, 10)
        driverService = new DriverService(ClockUtils.fixedClock(date))
        createStartAndEnd("123")
        createStartAndEnd("124")
        createStartAndEnd("125")
        createStartAndEndVip("125")
        createStartAndEndVip("126")
        when:
        def moneyForDay = operatorService.totalMoneyForDay(date)
        then:
        moneyForDay == Money.fromPlnZlote(1).multiply(3)
    }


    def createStartAndEnd(String number){
        def request = new ParkingStayStartRequest(number, "regular")
        driverService.start(request)
        driverService.end(number)
    }


    def createStartAndEndVip(String number){
        def request = new ParkingStayStartRequest(number, "vip")
        driverService.start(request)
        driverService.end(number)
    }

}