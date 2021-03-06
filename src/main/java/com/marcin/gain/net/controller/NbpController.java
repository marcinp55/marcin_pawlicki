package com.marcin.gain.net.controller;

import com.marcin.gain.net.client.NbpClient;
import com.marcin.gain.net.controller.validator.ControllerValidator;
import com.marcin.gain.net.dto.nbp.CalculatedNetPayDto;
import com.marcin.gain.net.dto.nbp.NbpRatesDto;
import com.marcin.gain.net.exception.ClientException;
import com.marcin.gain.net.helper.DateHelper;
import com.marcin.gain.net.service.PaymentCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/nbp")
@CrossOrigin("*")
public class NbpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NbpController.class);
    private final NbpClient nbpClient;
    private final PaymentCalculatorService paymentCalculatorService;
    private final ControllerValidator controllerValidator;
    private final DateHelper dateHelper;

    @Autowired
    public NbpController(NbpClient nbpClient,
                         PaymentCalculatorService paymentCalculatorService,
                         ControllerValidator controllerValidator,
                         DateHelper dateHelper) {
        this.nbpClient = nbpClient;
        this.paymentCalculatorService = paymentCalculatorService;
        this.controllerValidator = controllerValidator;
        this.dateHelper = dateHelper;
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
            return new CalculatedNetPayDto(paymentCalculatorService.calculateGross(dailyPay),
                                            paymentCalculatorService.grossToNet(fixedCost, tax, dailyPay));
        }

        String validRateDate = dateHelper.getCorrectRateDate(LocalDate.now());
        NbpRatesDto nbpRates = nbpClient.getRateByDate(currencyCode, validRateDate);

        if (nbpRates == null) {
            LOGGER.error("NBP Client returned no data.");
            throw new ClientException("Client returned no data.");
        }

        return new CalculatedNetPayDto(paymentCalculatorService.calculateGross(dailyPay),
                                        paymentCalculatorService.foreignGrossToNet(fixedCost, tax, dailyPay, nbpRates.getRates().get(0).getMidExchangeRate()));
    }
}
