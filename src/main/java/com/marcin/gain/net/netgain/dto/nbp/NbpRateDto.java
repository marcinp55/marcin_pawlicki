package com.marcin.gain.net.netgain.dto.nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbpRateDto {
    @JsonProperty("no")
    private String tableNumber;

    private String effectiveDate;

    @JsonProperty("mid")
    private double midExchangeRate;
}
