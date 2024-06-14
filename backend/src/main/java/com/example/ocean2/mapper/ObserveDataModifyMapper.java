package com.example.ocean2.mapper;


import com.example.ocean2.pojo.obv.AirObv;
import com.example.ocean2.pojo.obv.SstObv;
import com.example.ocean2.pojo.obv.WindObv;
import com.example.ocean2.pojo.other.DataSet;
import com.example.ocean2.pojo.other.SeaSite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;


/* 内部接口方便批量添加数据 */
@Mapper
public interface ObserveDataModifyMapper {

    @Insert("")
    int addDataSet(DataSet newDataSet);
    @Insert("")
    int addSeaSite(SeaSite newSeaSite);
    @Insert("")
    int addObvRecord(Integer dataSetID, Integer seaSiteID, LocalDate date,
                     Integer sstObvID, Integer windObvID, Integer airObvID);
    @Insert("")
    int addSstObv(SstObv sstObv);
    @Insert("")
    int addWindObv(WindObv windObv);
    @Insert("")
    int addAirObv(AirObv airObv);

    @Delete("")
    int delDataSet(Integer dataSetID);
    @Delete("")
    int delSeaSite(Integer seaSiteID);
}
