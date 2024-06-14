package com.example.ocean2.pojo.obv;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindObv {
    private Integer windObvID;
    private LocalDate date;
    private Float maxSpeed;
    private Float minSpeed;
    private Float avgSpeed;
    private Integer direction;
}
