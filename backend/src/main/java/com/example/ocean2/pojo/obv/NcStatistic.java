package com.example.ocean2.pojo.obv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NcStatistic {
    List<Float> sstValueList;
    List<String> ncRangeList;
}
