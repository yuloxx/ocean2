package com.example.ocean2.mapper;


import com.example.ocean2.pojo.obv.*;
import com.example.ocean2.pojo.other.DataSet;
import com.example.ocean2.pojo.other.SeaSite;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ObserveDataQueryMapper {
     List<DataSet> getDataSets();
     List<SeaSite> getSeaSites(Integer setID);
     List<SstObv> getSstObvs(Integer datasetID,Integer seaSiteID,LocalDate startDate, LocalDate endDate);
     List<WindObv> getWindObvs(Integer datasetID, Integer seaSiteID, LocalDate startDate, LocalDate endDate);
     List<AirObv> getAirObvs(Integer datasetID, Integer seaSiteID, LocalDate startDate, LocalDate endDate);

    List<NcFile> getNcFiles(LocalDate queryDate);

     List<NcData> getNcData(Integer ncFileID);

    List<NcFile> getNcFilesX(LocalDate startDate, LocalDate endDate);
}
