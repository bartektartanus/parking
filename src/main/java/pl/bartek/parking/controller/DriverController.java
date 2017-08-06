package pl.bartek.parking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.bartek.parking.service.DriverService;
import pl.bartek.parking.service.ParkingStayStartRequest;

@RestController
@RequestMapping(path = "/driver")
public class DriverController {

    private final DriverService driverService = new DriverService();

    @RequestMapping(path="/start")
    public boolean start(@RequestParam(name = "number") String number, @RequestParam(name = "type", defaultValue = "regular") String type){
        ParkingStayStartRequest startRequest = new ParkingStayStartRequest(number, type);
        driverService.start(startRequest);
        return true;
    }


    @RequestMapping(path = "/end")
    public boolean end(@RequestParam(name = "number") String number){
        driverService.end(number);
        return true;
    }


    @RequestMapping(path = "/checkCharge")
    public String checkCharge(@RequestParam(name = "number") String number){
        return driverService.checkCharge(number).toString();
    }
}
