package com.example.ocean2.controller;

import com.example.ocean2.pojo.obv.*;
import com.example.ocean2.pojo.other.DataSet;
import com.example.ocean2.pojo.other.SeaSite;
import com.example.ocean2.pojo.web.BadRequestMessageEnum;
import com.example.ocean2.pojo.web.Result;
import com.example.ocean2.service.ObserveDataQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ObserveDataQueryController {
    @Autowired
    ObserveDataQueryService observeDataQueryService;
    @GetMapping("/obv/dataset")
    public Result getDataSets(){
        List<DataSet> dataSetList = observeDataQueryService.getDataSets();
        return Result.success(dataSetList);
    }

    @GetMapping("/obv/seasite")
    public Result getSeaSites(Integer dataSetID)  {
        if(dataSetID == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_CANNOT_NULL.getMsg());
        }
        List<SeaSite> seaSiteList = observeDataQueryService.getSeaSites(dataSetID);
        return Result.success(seaSiteList);
    }

    @GetMapping("/obv/sst")
    public Result getSstObvs(Integer dataSetID,Integer seaSiteID,LocalDate startDate,LocalDate endDate){
        if(dataSetID == null || seaSiteID == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_ILLEGAL_NULL.getMsg());
        }
        List<SstObv> sstObvList = observeDataQueryService.getSstObvs(dataSetID,seaSiteID,startDate,endDate);
        return Result.success(sstObvList);
    }
    @GetMapping("/obv/wind")
    public Result getWindObvs(Integer dataSetID,Integer seaSiteID,LocalDate startDate,LocalDate endDate){
        if(dataSetID == null || seaSiteID == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_ILLEGAL_NULL.getMsg());
        }
        List<WindObv> windObvList = observeDataQueryService.getWindObvs(dataSetID,seaSiteID,startDate,endDate);
        return Result.success(windObvList);
    }
    @GetMapping("/obv/air")
    public Result getAirObvs(Integer dataSetID,Integer seaSiteID,LocalDate startDate,LocalDate endDate){
        if(dataSetID == null || seaSiteID == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_ILLEGAL_NULL.getMsg());
        }
        List<AirObv> airObvList = observeDataQueryService.getAirObvs(dataSetID,seaSiteID,startDate,endDate);
        return Result.success(airObvList);
    }

    @GetMapping("/obv/ncfile")
    public Result getNcFile(LocalDate queryDate){
        if(queryDate == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_CANNOT_NULL.getMsg());
        }
        NcFile ncFile = observeDataQueryService.getNcFile(queryDate);
        return Result.success(ncFile);
    }
    @GetMapping("/obv/ncdata")
    public Result getNcFile(Integer ncFileID){
        if(ncFileID == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_CANNOT_NULL.getMsg());
        }
        List<NcData> ncDataList = observeDataQueryService.getNcData(ncFileID);
        return Result.success(ncDataList);
    }
    @GetMapping("/obv/ncStatistic")
    public Result getNcStatistic(LocalDate startDate, LocalDate endDate, Float lat, Float lon){
        if(startDate == null || endDate == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_CANNOT_NULL.getMsg());
        }
        if(lat == null || lon == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_CANNOT_NULL.getMsg());
        }
        NcStatistic ncStatistic = observeDataQueryService.getNcStatistic(startDate, endDate, lat, lon);
        return Result.success(ncStatistic);
    }
}
