package com.marcin.gain.net.dto.nbp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculatedNetPayDto {
    private double grossPay;
    private double netPay;
}
