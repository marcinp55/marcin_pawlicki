package com.marcin.gain.net.client;

import com.marcin.gain.net.dto.nbp.NbpRateDto;
import com.marcin.gain.net.dto.nbp.NbpRatesDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NbpClientTestSuite {
    @InjectMocks
    private NbpClient nbpClient;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void shouldFetchRateByDate() throws URISyntaxException {
        // Given
        NbpRateDto nbpRateDto = new NbpRateDto("036/A/NBP/2019", "2019-02-20", 4.3371);
        List<NbpRateDto> nbpRateDtos = new ArrayList<>();

        nbpRateDtos.add(nbpRateDto);

        NbpRatesDto nbpRatesDto = new NbpRatesDto("A", "euro", "EUR", nbpRateDtos);
        URI uri = new URI("http://api.nbp.pl/api/exchangerates/rates/a/eur/2019-02-20");

        Mockito.when(restTemplate.getForObject(uri, NbpRatesDto.class)).thenReturn(nbpRatesDto);

        // When
        NbpRatesDto nbpRatesResult = nbpClient.getRateByDate("eur", "2019-02-20");

        // Then
        Assert.assertEquals("A", nbpRatesResult.getTableType());
        Assert.assertEquals("euro", nbpRatesResult.getCurrencyName());
        Assert.assertEquals("EUR", nbpRatesResult.getCurrencyCode());
        Assert.assertEquals("036/A/NBP/2019", nbpRatesResult.getRates().get(0).getTableNumber());
        Assert.assertEquals("2019-02-20", nbpRatesResult.getRates().get(0).getEffectiveDate());
        Assert.assertEquals(4.3371, nbpRatesResult.getRates().get(0).getMidExchangeRate(), 0);
    }

    @Test
    public void shouldReturnEmptyResult() throws URISyntaxException {
        // Given
        URI uri = new URI("http://api.nbp.pl/api/exchangerates/rates/a/eur/2019-02-20");
        BDDMockito.willThrow(new RestClientException("Client exception")).given(restTemplate).getForObject(uri, NbpRatesDto.class);

        // When
        NbpRatesDto nbpRatesResult = nbpClient.getRateByDate("eur", "2019-02-20");

        // Then
        Assert.assertNull(nbpRatesResult.getTableType());
        Assert.assertNull(nbpRatesResult.getCurrencyName());
        Assert.assertNull(nbpRatesResult.getCurrencyCode());
        Assert.assertNull(nbpRatesResult.getRates());
    }
}
