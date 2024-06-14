package com.example.ocean2.service;

import com.example.ocean2.mapper.ObserveDataQueryMapper;
import com.example.ocean2.pojo.obv.*;
import com.example.ocean2.pojo.other.DataSet;
import com.example.ocean2.pojo.other.SeaSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class ObserveDataQueryService {

    @Autowired
    ObserveDataQueryMapper observeDataQueryMapper;
    public List<SeaSite> getSeaSites(Integer setID) {
        List<SeaSite> seaSiteList = observeDataQueryMapper.getSeaSites(setID);
        return seaSiteList;
    }

    public List<DataSet> getDataSets() {
        List<DataSet> dataSetList = observeDataQueryMapper.getDataSets();
        return dataSetList;
    }
    public List<SstObv> getSstObvs(Integer dataSetID, Integer seaSiteID, LocalDate startDate, LocalDate endDate) {
        List<SstObv> sstObvList = observeDataQueryMapper.getSstObvs(dataSetID,seaSiteID,startDate,endDate);
        return sstObvList;
    }
    public List<WindObv> getWindObvs(Integer dataSetID,Integer seaSiteID, LocalDate startDate, LocalDate endDate) {
        List<WindObv> windObvList = observeDataQueryMapper.getWindObvs(dataSetID,seaSiteID,startDate,endDate);
        return windObvList;
    }
    public List<AirObv> getAirObvs(Integer dataSetID, Integer seaSiteID, LocalDate startDate, LocalDate endDate) {
        List<AirObv> airObvList = observeDataQueryMapper.getAirObvs(dataSetID,seaSiteID,startDate,endDate);
        return airObvList;
    }

    public NcFile getNcFile(LocalDate queryDate) {
        List<NcFile> ncFileList = observeDataQueryMapper.getNcFiles(queryDate);
        if(!ncFileList.isEmpty())
            return ncFileList.get(0);
        return null;
    }

    public List<NcData> getNcData(Integer ncFileID) {
        List<NcData> ncDataList = observeDataQueryMapper.getNcData(ncFileID);

        float maxValue = Float.MIN_VALUE;
        float minValue = Float.MAX_VALUE;

        for(NcData item : ncDataList){
            maxValue = Math.max(maxValue, item.getCount());
            minValue = Math.min(minValue, item.getCount());
        }

        for(NcData item : ncDataList){
            item.setCount(
                    (item.getCount() - minValue) / (maxValue - minValue)
            );
        }
        return ncDataList;
    }

    public NcStatistic getNcStatistic(LocalDate startDate, LocalDate endDate, Float lat, Float lon) {
        ArrayList<Float> sstValueList  = new ArrayList<>();
        ArrayList<String> ncRangeList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<NcFile> ncFileList = observeDataQueryMapper.getNcFilesX(startDate, endDate);

        for(NcFile ncFile : ncFileList){
            List<NcData> ncDataList = observeDataQueryMapper.getNcData(ncFile.getNcFileID());
            OptionalDouble averageData = ncDataList.stream()
                    .filter(item -> {
                        float latCompare = Math.abs(item.getLat() - lat);
                        float lonCompare = Math.abs(item.getLng() - lon);
                        return (latCompare < 1) && (lonCompare < 1);
                    })
                    .mapToDouble(NcData::getCount)
                    .average();

            averageData.ifPresent(doubleValue -> {
                String startDateString = ncFile.getStartDate().format(formatter);
                String endDateString = ncFile.getEndDate().format(formatter);
                ncRangeList.add(startDateString + " to " + endDateString);

                sstValueList.add((float)doubleValue);
            });
        }
        return new NcStatistic(sstValueList, ncRangeList);
    }
}
