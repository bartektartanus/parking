package pl.bartek.parking.service;

public class ParkingStayStartRequest {

    private String vehicleRegistrationNumber;
    private String customerType;

    public ParkingStayStartRequest(String vehicleRegistrationNumber, String customerType) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.customerType = customerType;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public boolean isVip(){
        return "vip".equals(customerType.toLowerCase());
    }
}
