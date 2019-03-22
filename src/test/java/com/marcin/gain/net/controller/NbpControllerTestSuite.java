package com.marcin.gain.net.controller;

import com.marcin.gain.net.client.NbpClient;
import com.marcin.gain.net.dto.nbp.NbpRateDto;
import com.marcin.gain.net.dto.nbp.NbpRatesDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NbpControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NbpClient nbpClient;

    @Test
    public void shouldFetchNetPay() {
        // Given
        String date = LocalDate.now().toString();
        NbpRateDto nbpRateDto = new NbpRateDto("036/A/NBP/2019", date, 4.3371);
        List<NbpRateDto> nbpRateDtos = new ArrayList<>();

        nbpRateDtos.add(nbpRateDto);

        NbpRatesDto nbpRatesDto = new NbpRatesDto("A", "euro", "EUR", nbpRateDtos);

        Mockito.when(nbpClient.getRateByDate(any(), any())).thenReturn(nbpRatesDto);

        // When&Then
        try {
            mockMvc.perform(get("/nbp/net")
                        .param("fixedCost", "500")
                        .param("tax", "20")
                        .param("dailyPay", "150")
                        .param("currCode", "EUR"))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.netPay", is(9281.394)));
        } catch (Exception e) {
            fail(e.getCause().toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnInvalidData() {
        // Given
        String date = LocalDate.now().toString();
        NbpRateDto nbpRateDto = new NbpRateDto("036/A/NBP/2019", date, 4.3371);
        List<NbpRateDto> nbpRateDtos = new ArrayList<>();

        nbpRateDtos.add(nbpRateDto);

        NbpRatesDto nbpRatesDto = new NbpRatesDto("A", "euro", "EUR", nbpRateDtos);

        Mockito.when(nbpClient.getRateByDate(any(), any())).thenReturn(nbpRatesDto);

        // When&Then
        try {
            mockMvc.perform(get("/nbp/net")
                        .param("fixedCost", "-500")
                        .param("tax", "202")
                        .param("dailyPay", "150")
                        .param("currCode", "EUR"))
                            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                            .andExpect(jsonPath("$.message", containsString("Invalid data")));
        } catch (Exception e) {
            fail(e.getCause().toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnClientException() {
        // Given
        Mockito.when(nbpClient.getRateByDate(any(), any())).thenReturn(null);

        // When&Then
        try {
            mockMvc.perform(get("/nbp/net")
                    .param("fixedCost", "500")
                    .param("tax", "20")
                    .param("dailyPay", "150")
                    .param("currCode", "EUR"))
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.message", containsString("Client returned no data")));
        } catch (Exception e) {
            fail(e.getCause().toString());
            e.printStackTrace();
        }
    }
}
