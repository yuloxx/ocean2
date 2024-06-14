package com.example.ocean2.pojo.other;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSet {
    private Integer dataSetID;
    private String dataSetName;
    private String description;
}
