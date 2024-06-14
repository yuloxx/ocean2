package com.example.ocean2.pojo.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceView {
    private Integer resourceID;
    private String resourceName;
    private String unitName;
    private Float lat;
    private Float lon;
    private Boolean hasQuota;
    private BigDecimal quota;
}
