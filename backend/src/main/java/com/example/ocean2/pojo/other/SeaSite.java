package com.example.ocean2.pojo.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeaSite {
    private Integer seaSiteID;
    private String seaSiteName;
    private Float lat;
    private Float lon;
}
