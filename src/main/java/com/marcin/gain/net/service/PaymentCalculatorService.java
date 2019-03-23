package com.marcin.gain.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentCalculatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentCalculatorService.class);
    private final int MONTH_LENGTH = 22;

    public double calculateGross(double dailyPay) {
        return dailyPay * MONTH_LENGTH;
    }

    public double grossToNet(double fixedCost, double tax, double dailyPay) {
        if (fixedCost < 0 || tax < 0 || tax > 100 || dailyPay < 0) {
            LOGGER.error("Invalid fixed cost or tax or daily payment.");
            return 0;
        }

        BigDecimal monthlyPay = BigDecimal.valueOf(calculateGross(dailyPay));
        BigDecimal calculatedTax = monthlyPay.multiply(BigDecimal.valueOf(tax / 100));
        BigDecimal plnNetPay = monthlyPay.subtract(BigDecimal.valueOf(fixedCost)).subtract(calculatedTax);

        return plnNetPay.doubleValue();
    }

    public double foreignGrossToNet(double fixedCost, double tax, double dailyPay, double exchangeRate) {
        BigDecimal netPay = BigDecimal.valueOf(grossToNet(fixedCost, tax, dailyPay));

        if (exchangeRate < 0 || netPay.doubleValue() == 0) {
            LOGGER.error("Invalid exchange rate or calculated net value.");
            return 0;
        }

        BigDecimal plnNetPay = netPay.multiply(BigDecimal.valueOf(exchangeRate));

        return plnNetPay.doubleValue();
    }
}
