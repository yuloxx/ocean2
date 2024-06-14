package com.example.ocean2.pojo.obv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class AirObv {
    private Integer airObvID;
    private LocalDate date;
    private Float temp;
    private Float pressure;
    private Integer visibility;
}
