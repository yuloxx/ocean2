package com.example.ocean2.pojo.other;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facility {
    private Integer facilityID;
    private Integer seaSiteID;
    private String facilityName;
    private String description;
}
