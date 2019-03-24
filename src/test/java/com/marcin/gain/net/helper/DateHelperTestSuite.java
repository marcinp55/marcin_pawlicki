package com.marcin.gain.net.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateHelperTestSuite {
    @Autowired
    private DateHelper dateHelper;

    @Test
    public void shouldReturnCorrectRateDate() {
        // Given
        LocalDate mondayDate = LocalDate.of(2019, 3, 25);
        LocalDate sundayDate = LocalDate.of(2019, 3, 24);
        LocalDate saturdayDate = LocalDate.of(2019, 3, 23);
        LocalDate wednesdayDate = LocalDate.of(2019, 3, 20);

        // When
        String mondayCorrectDate = dateHelper.getCorrectRateDate(mondayDate);
        String sundayCorrectDate = dateHelper.getCorrectRateDate(sundayDate);
        String saturdayCorrectDate = dateHelper.getCorrectRateDate(saturdayDate);
        String wednesdayCorrectDate = dateHelper.getCorrectRateDate(wednesdayDate);

        // Then
        Assert.assertEquals("2019-03-19", wednesdayCorrectDate);
        Assert.assertEquals("2019-03-22", saturdayCorrectDate);
        Assert.assertEquals("2019-03-22", sundayCorrectDate);
        Assert.assertEquals("2019-03-22", mondayCorrectDate);
    }
}
