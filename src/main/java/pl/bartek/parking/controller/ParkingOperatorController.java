package pl.bartek.parking.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.bartek.parking.service.ParkingOperatorService;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/operator")
public class ParkingOperatorController {

    private final ParkingOperatorService parkingOperatorService = new ParkingOperatorService();

    @RequestMapping("/status")
    public boolean status(@RequestParam(value="number") String number) {
        return parkingOperatorService.isStarted(number);
    }


    @RequestMapping("/total")
    public String total(@RequestParam(value="date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        if(date == null){
            date = LocalDate.now();
        }
        return parkingOperatorService.totalMoneyForDay(date).toString();
    }
}