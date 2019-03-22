package com.marcin.gain.net.dto.nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbpRatesDto {
    @JsonProperty("table")
    private String tableType;

    @JsonProperty("currency")
    private String currencyName;

    @JsonProperty("code")
    private String currencyCode;

    private List<NbpRateDto> rates;
}
