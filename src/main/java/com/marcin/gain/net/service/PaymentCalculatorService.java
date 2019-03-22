package com.marcin.gain.net.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentCalculatorService {
    private final int MONTH_LENGTH = 22;

    public double grossToNet(double fixedCost, double tax, double dailyPay) {
        BigDecimal monthlyPay = BigDecimal.valueOf(dailyPay).multiply(BigDecimal.valueOf(MONTH_LENGTH));
        BigDecimal calculatedTax = monthlyPay.multiply(BigDecimal.valueOf(tax / 100));
        BigDecimal plnNetPay = monthlyPay.subtract(BigDecimal.valueOf(fixedCost)).subtract(calculatedTax);

        return plnNetPay.doubleValue();
    }

    public double foreignGrossToNet(double fixedCost, double tax, double dailyPay, double exchangeRate) {
        BigDecimal netPay = BigDecimal.valueOf(grossToNet(fixedCost, tax, dailyPay));
        BigDecimal plnNetPay = netPay.multiply(BigDecimal.valueOf(exchangeRate));

        return plnNetPay.doubleValue();
    }
}
