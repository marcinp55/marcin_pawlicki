package com.marcin.gain.net.controller;

import com.marcin.gain.net.client.NbpClient;
import com.marcin.gain.net.controller.validator.ControllerValidator;
import com.marcin.gain.net.dto.nbp.CalculatedNetPayDto;
import com.marcin.gain.net.dto.nbp.NbpRatesDto;
import com.marcin.gain.net.service.PaymentCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/nbp")
public class NbpController {
    private final NbpClient nbpClient;
    private final PaymentCalculatorService paymentCalculatorService;
    private final ControllerValidator controllerValidator;

    @Autowired
    public NbpController(NbpClient nbpClient, PaymentCalculatorService paymentCalculatorService, ControllerValidator controllerValidator) {
        this.nbpClient = nbpClient;
        this.paymentCalculatorService = paymentCalculatorService;
        this.controllerValidator = controllerValidator;
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "net",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculatedNetPayDto getGrossToNet(@RequestParam double fixedCost,
                                             @RequestParam double tax,
                                             @RequestParam double dailyPay,
                                             @RequestParam(name = "currCode") String currencyCode) {
        controllerValidator.validateNetPayRequest(fixedCost, tax, dailyPay);

        if (currencyCode.toUpperCase().equals("PLN")) {
            return new CalculatedNetPayDto(paymentCalculatorService.grossToNet(fixedCost, tax, dailyPay));
        }

        String dateMinusOne = LocalDate.now().minusDays(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        NbpRatesDto nbpRates = nbpClient.getRateByDate(currencyCode, dateMinusOne);

        return new CalculatedNetPayDto(paymentCalculatorService.foreignGrossToNet(fixedCost, tax, dailyPay, nbpRates.getRates().get(0).getMidExchangeRate()));
    }
}
