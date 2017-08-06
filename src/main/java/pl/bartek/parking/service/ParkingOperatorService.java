package pl.bartek.parking.service;

import pl.bartek.parking.dao.ParkingStayDao;
import pl.bartek.parking.dao.ParkingStayDaoImpl;
import pl.bartek.parking.model.Money;
import pl.bartek.parking.model.ParkingStay;

import java.time.LocalDate;

public class ParkingOperatorService {

    private ParkingStayDao parkingStayDao = ParkingStayDaoImpl.getInstance();

    public boolean isStarted(String vehicleRegistrationNumber){
        return parkingStayDao.findByVehicleNumberAndNotEnded(vehicleRegistrationNumber)
                .map(ParkingStay::isStarted)
                .getOrElse(false);
    }

    public Money totalMoneyForDay(LocalDate date){
        return parkingStayDao.findAllByEndDate(date)
                .map(ParkingStay::calculateCharge)
                .reduceOption(Money::add)
                .getOrElse(Money.fromPlnZlote(0));
    }

}
