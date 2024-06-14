package com.example.ocean2.pojo.resource;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private Integer resourceID;
    private Integer resourceTypeID;
    private Integer seaSiteID;
    private BigDecimal quota;
}
