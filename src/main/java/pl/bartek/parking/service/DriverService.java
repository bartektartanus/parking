package pl.bartek.parking.service;

import io.vavr.control.Option;
import pl.bartek.parking.dao.ParkingStayDao;
import pl.bartek.parking.dao.ParkingStayDaoImpl;
import pl.bartek.parking.model.Money;
import pl.bartek.parking.model.ParkingStay;
import pl.bartek.parking.model.ParkingStayFactory;

import java.time.Clock;

public class DriverService {

    private final Clock clock;
    private ParkingStayDao parkingStayDao = ParkingStayDaoImpl.getInstance();

    public DriverService() {
        this(Clock.systemDefaultZone());
    }

    public DriverService(Clock clock) {
        this.clock = clock;
    }

    public void start(ParkingStayStartRequest startRequest){
        Option<ParkingStay> byVehicleNumber = parkingStayDao.findByVehicleNumberAndNotEnded(startRequest.getVehicleRegistrationNumber());
        if(byVehicleNumber.map(ParkingStay::isStarted).getOrElse(false)){
            throw new ParkingStayOperationException("Another stay was started and not finish - can't start another");
        }
        ParkingStay parkingStay = ParkingStayFactory.fromRequest(startRequest);
        parkingStay.start(clock);
        parkingStayDao.save(parkingStay);
    }

    public void end(String vehicleRegistrationNumber){
        Option<ParkingStay> byVehicleNumber = parkingStayDao.findByVehicleNumberAndNotEnded(vehicleRegistrationNumber);
        if(byVehicleNumber.map(ParkingStay::isEnded).getOrElse(true)){
            throw new ParkingStayOperationException("Stay for this vehicle number was not found or already ended");
        }
        byVehicleNumber.forEach(p -> p.end(clock));
    }

    public Money checkCharge(String vehicleRegistrationNumber){
        return parkingStayDao.findByVehicleNumberAndNotPaid(vehicleRegistrationNumber)
                .map(ParkingStay::calculateCharge)
                .getOrElse(Money.fromPlnZlote(0));
    }

    static class ParkingStayOperationException extends RuntimeException{
        ParkingStayOperationException(String message) {
            super(message);
        }
    }

}
