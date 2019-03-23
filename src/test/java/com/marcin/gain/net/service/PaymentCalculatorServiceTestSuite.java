package com.marcin.gain.net.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentCalculatorServiceTestSuite {
    @Autowired
    private PaymentCalculatorService paymentCalculatorService;

    @Test
    public void shouldFetchGrossPay() {
        // Given
        double dailyPay = 100;

        // When
        double grossPay = paymentCalculatorService.calculateGross(dailyPay);

        // Then
        Assert.assertEquals(2200, grossPay, 0);
    }

    @Test
    public void shouldFetchZeroGrossPay() {

    }

    @Test
    public void shouldFetchGrossToNetPay() {
        // Given
        double fixedCost = 500;
        double tax = 20;
        double dailyPay = 150;

        // When
        double netPay = paymentCalculatorService.grossToNet(fixedCost, tax, dailyPay);

        // Then
        Assert.assertEquals(2140, netPay, 0);
    }

    @Test
    public void shouldFetchForeignGrossToNetPay() {
        // Given
        double fixedCost = 1000;
        double tax = 15;
        double dailyPay = 200;
        double exchangeRate = 4.33;

        // When
        double netPay = paymentCalculatorService.foreignGrossToNet(fixedCost, tax, dailyPay, exchangeRate);

        // Then
        Assert.assertEquals(11864.2 , netPay, 0);
    }

    @Test
    public void shouldFetchZeroNetPay() {
        // Given
        double fixedCost = 500;
        double tax = 10;
        double dailyPay = -30;

        // When
        double netPay = paymentCalculatorService.grossToNet(fixedCost, tax, dailyPay);

        // Then
        Assert.assertEquals(0, netPay, 0);
    }

    @Test
    public void shouldFetchZeroForeignNetPay() {
        // Given
        double fixedCost = 600;
        double tax = 120;
        double dailyPay = 220;
        double exchangeRate = 4.15;

        // When
        double netPay = paymentCalculatorService.foreignGrossToNet(fixedCost, tax, dailyPay, exchangeRate);

        // Then
        Assert.assertEquals(0, netPay, 0);
    }
}
