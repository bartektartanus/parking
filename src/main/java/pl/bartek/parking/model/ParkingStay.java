package pl.bartek.parking.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParkingStay {

    private String vehicleRegistrationNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // additional fields for faster lookup of stays for total charge calculation
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paid = false;
    private CustomerType customerType;

    ParkingStay(String vehicleRegistrationNumber, CustomerType customerType) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.customerType = customerType;
    }

    public void start(Clock clock){
        startTime = LocalDateTime.now(clock);
        startDate = startTime.toLocalDate();
    }

    public void end(Clock clock){
        endTime = LocalDateTime.now(clock);
        endDate = endTime.toLocalDate();
    }

    public boolean isStarted(){
        return startTime != null && endTime == null;
    }

    public boolean isEnded(){
        return startTime != null && endTime != null;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public boolean isEndedOn(LocalDate date) {
        return isEnded() && endDate.isEqual(date);
    }

    public boolean isPaid() {
        return paid;
    }

    public Money calculateCharge(){
        // if endDate is null then the vehicle is still on the parking lot, so we should calculate current charge
        LocalDateTime end = endTime == null ? LocalDateTime.now() : endTime;
        return customerType.getStrategy().calculate(startTime, end);
    }

}
