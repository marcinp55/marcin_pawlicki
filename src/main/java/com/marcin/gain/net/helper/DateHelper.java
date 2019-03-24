package com.marcin.gain.net.helper;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateHelper {
    public String getCorrectRateDate(LocalDate currentDate) {
        DayOfWeek currentDay = currentDate.getDayOfWeek();
        LocalDate correctDate;

        switch (currentDay) {
            case SUNDAY: {
                correctDate = currentDate.minusDays(2L);
                break;
            }
            case MONDAY: {
                correctDate = currentDate.minusDays(3L);
                break;
            }
            default: {
                correctDate = currentDate.minusDays(1L);
                break;
            }
        }

        return correctDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
