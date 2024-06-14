package com.example.ocean2.pojo.obv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SstObv {
    private Integer sstObvID;
    private LocalDate date;
    private Float maxSst;
    private Float minSst;
    private Float avgSst;
}
