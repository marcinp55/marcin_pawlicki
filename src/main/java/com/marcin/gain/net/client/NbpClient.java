package com.marcin.gain.net.client;

import com.marcin.gain.net.dto.nbp.NbpRatesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NbpClient {
    private final RestTemplate restTemplate;

    @SuppressWarnings("FieldCanBeLocal") // Not local for additional requests in future
    private final String NBP_RATES_API_ENDPOINT_ROOT = "http://api.nbp.pl/api/exchangerates/";

    @Autowired
    public NbpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NbpRatesDto getRateByDate(String currencyCode, String date) {
        URI requestUrl = UriComponentsBuilder
                .fromHttpUrl(NBP_RATES_API_ENDPOINT_ROOT +
                                String.join("/", "rates/a", currencyCode, date))
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(requestUrl, NbpRatesDto.class);
    }
}
