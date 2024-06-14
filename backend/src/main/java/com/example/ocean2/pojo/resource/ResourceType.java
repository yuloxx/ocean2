package com.example.ocean2.pojo.resource;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceType {
    private Integer resourceTypeID;
    private String resourceName;
    private String unitName;
    private Boolean hasQuota;
}
