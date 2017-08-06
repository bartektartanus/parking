package pl.bartek.parking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/driver")
public class DriverController {

    @RequestMapping
    public void start(@RequestParam(name = "number") String number, @RequestParam(name = "type", defaultValue = "regular") String type){

    }


    @RequestMapping
    public void end(@RequestParam(name = "number") String number){

    }


    @RequestMapping
    public String checkCharge(@RequestParam(name = "number") String number){
        return "0.00 PLN";
    }
}
