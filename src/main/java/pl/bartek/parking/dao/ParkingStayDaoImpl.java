package pl.bartek.parking.dao;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.bartek.parking.model.ParkingStay;

import java.time.LocalDate;

public class ParkingStayDaoImpl implements ParkingStayDao {

    private static final ParkingStayDao INSTANCE = new ParkingStayDaoImpl();

    private List<ParkingStay> entities = List.empty();

    private ParkingStayDaoImpl() {}

    public static ParkingStayDao getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(ParkingStay parkingStay) {
        this.entities = entities.append(parkingStay);
    }

    @Override
    public Option<ParkingStay> findByVehicleNumberAndNotEnded(String vehicleNumber) {
        return entities.filter(e -> e.getVehicleRegistrationNumber().equals(vehicleNumber) && !e.isEnded())
                .headOption();
    }

    @Override
    public Option<ParkingStay> findByVehicleNumberAndNotPaid(String vehicleNumber) {
        return entities.filter(e -> e.getVehicleRegistrationNumber().equals(vehicleNumber) && !e.isPaid())
                .headOption();
    }

    @Override
    public List<ParkingStay> findAllByEndDate(LocalDate date) {
        return entities.filter(e -> e.isEndedOn(date));
    }
}
