package com.marcin.gain.net.netgain.client;

import com.marcin.gain.net.netgain.dto.nbp.NbpRatesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NbpClient {
    private RestTemplate restTemplate;

    private final String nbpRatesApiEndpointRoot = "http://api.nbp.pl/api/exchangerates/";

    @Autowired
    public NbpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NbpRatesDto getRateByDate(String tableType, String currencyCode, String date) {
        URI requestUrl = UriComponentsBuilder
                .fromHttpUrl(nbpRatesApiEndpointRoot +
                                String.join("/", "rates", tableType, currencyCode, date))
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(requestUrl, NbpRatesDto.class);
    }
}
