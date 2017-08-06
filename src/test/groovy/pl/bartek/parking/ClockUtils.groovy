package pl.bartek.parking

import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

class ClockUtils {

    static Clock fixedClock(LocalDate date){
        return Clock.fixed(LocalDateTime.of(date, LocalTime.of(10,0)).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    }

}
