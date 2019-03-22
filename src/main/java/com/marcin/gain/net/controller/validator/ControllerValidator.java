package com.marcin.gain.net.controller.validator;

import com.marcin.gain.net.exception.InvalidDataException;
import org.springframework.stereotype.Component;

@Component
public class ControllerValidator {
    public void validateNetPayRequest(double fixedCost, double tax, double dailyPay) {
        if (fixedCost < 0 || tax < 0 || tax > 100 || dailyPay < 0) {
            throw new InvalidDataException("Invalid data to calculate net pay. " +
                                            "Fixed cost and daily pay greater or equal than 0, " +
                                            "Tax between 0 and 100");
        }
    }
}
