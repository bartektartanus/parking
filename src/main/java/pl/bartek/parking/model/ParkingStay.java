package pl.bartek.parking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParkingStay {

    private String vehicleRegistrationNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // additional fields for faster lookup of stays for total charge calculation
    private LocalDate startDate;
    private LocalDate endDate;
    private CustomerType customerType;

    private ParkingStay(String vehicleRegistrationNumber, CustomerType customerType) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.customerType = customerType;
    }

    public static ParkingStay regular(String vehicleRegistrationNumber){
        return new ParkingStay(vehicleRegistrationNumber, CustomerType.REGULAR);
    }

    public static ParkingStay vip(String vehicleRegistrationNumber){
        return new ParkingStay(vehicleRegistrationNumber, CustomerType.VIP);
    }

    public void start(){
        startTime = LocalDateTime.now();
        startDate = startTime.toLocalDate();
    }

    public void end(){
        endTime = LocalDateTime.now();
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

    public CustomerType getCustomerType() {
        return customerType;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Money calculateCharge(){
        // if endDate is null then the vehicle is still on the parking lot, so we should calculate current charge
        LocalDateTime end = endTime == null ? LocalDateTime.now() : endTime;
        return customerType.getStrategy().calculate(startTime, end);
    }

}
