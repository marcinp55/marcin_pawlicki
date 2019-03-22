package com.marcin.gain.net.netgain.controller;

import com.marcin.gain.net.netgain.client.NbpClient;
import com.marcin.gain.net.netgain.dto.nbp.CalculatedNetPayDto;
import com.marcin.gain.net.netgain.dto.nbp.NbpRatesDto;
import com.marcin.gain.net.netgain.service.PaymentCalculatorService;
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

    @Autowired
    public NbpController(NbpClient nbpClient, PaymentCalculatorService paymentCalculatorService) {
        this.nbpClient = nbpClient;
        this.paymentCalculatorService = paymentCalculatorService;
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "net",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculatedNetPayDto getForeignGrossToNet(@RequestParam double fixedCost,
                                                    @RequestParam double tax,
                                                    @RequestParam double dailyPay,
                                                    @RequestParam(name = "currCode") String currencyCode) {
        if (currencyCode.toUpperCase().equals("PLN")) {
            return new CalculatedNetPayDto(paymentCalculatorService.grossToNet(fixedCost, tax, dailyPay));
        }

        String dateMinusOne = LocalDate.now().minusDays(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        NbpRatesDto nbpRates = nbpClient.getRateByDate(currencyCode, dateMinusOne);

        return new CalculatedNetPayDto(paymentCalculatorService.foreignGrossToNet(fixedCost, tax, dailyPay, nbpRates.getRates().get(0).getMidExchangeRate()));
    }
}
