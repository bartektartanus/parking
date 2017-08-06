package pl.bartek.parking.model;

import pl.bartek.parking.service.ParkingStayStartRequest;

public class ParkingStayFactory {

    public static ParkingStay fromRequest(ParkingStayStartRequest startRequest){
        if(startRequest.isVip()){
            return vip(startRequest.getVehicleRegistrationNumber());
        }else{
            return regular(startRequest.getVehicleRegistrationNumber());
        }
    }

    public static ParkingStay regular(String vehicleRegistrationNumber){
        return new ParkingStay(vehicleRegistrationNumber, CustomerType.REGULAR);
    }

    public static ParkingStay vip(String vehicleRegistrationNumber){
        return new ParkingStay(vehicleRegistrationNumber, CustomerType.VIP);
    }

}
