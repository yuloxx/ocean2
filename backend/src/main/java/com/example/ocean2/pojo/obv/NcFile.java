package com.example.ocean2.pojo.obv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NcFile {
    private Integer ncFileID;
    private String ncFileName;
    private LocalDate startDate;
    private LocalDate endDate;
}
