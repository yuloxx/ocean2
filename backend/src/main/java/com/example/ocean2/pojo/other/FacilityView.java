package com.example.ocean2.pojo.other;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityView {
    private Integer facilityID;
    private String facilityName;
    private String description;
    private Float lat;
    private Float lon;
}
