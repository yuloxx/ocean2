package com.example.ocean2.pojo.obv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NcData {
    private Float lat;
    private Float lng;
    private Float count;
}
