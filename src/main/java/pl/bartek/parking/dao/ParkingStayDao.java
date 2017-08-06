package pl.bartek.parking.dao;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.bartek.parking.model.ParkingStay;

import java.time.LocalDate;


public interface ParkingStayDao {

    void save(ParkingStay parkingStay);
    Option<ParkingStay> findByVehicleNumberAndNotEnded(String vehicleNumber);
    Option<ParkingStay> findByVehicleNumberAndNotPaid(String vehicleNumber);
    List<ParkingStay> findAllByEndDate(LocalDate date);

}
