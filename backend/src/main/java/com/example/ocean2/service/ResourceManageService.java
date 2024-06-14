package com.example.ocean2.service;


import com.example.ocean2.exception.ForeignKeyColumnMissException;
import com.example.ocean2.exception.ResourceTypeUsingException;
import com.example.ocean2.mapper.ResourceManageMapper;
import com.example.ocean2.pojo.other.Facility;
import com.example.ocean2.pojo.other.FacilityView;
import com.example.ocean2.pojo.other.SeaSite;
import com.example.ocean2.pojo.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResourceManageService {
    @Autowired
    ResourceManageMapper resourceManageMapper;

    public List<Facility> getFacilities(){
        List<Facility> facilityList = resourceManageMapper.getFacilities();
        return facilityList;
    }
    public List<ResourceType> getResourceType() {
        List<ResourceType> resourceTypeList = resourceManageMapper.getResourceType();
        return resourceTypeList;
    }

    public List<ResourceView> getResourceViews(Integer resourceTypeID){
        List<ResourceView> resourceViewList = resourceManageMapper.getResourceViews(resourceTypeID);
        return resourceViewList;
    }

    public List<ResourceExploitView> getResourceExploitViews(Integer resourceTypeID, Integer facilityID,
                                                       LocalDate startData, LocalDate endDate){
        List<ResourceExploitView> resourceExploitViewList =
                resourceManageMapper.getResourceExploitViews(resourceTypeID,facilityID,startData,endDate);
        return resourceExploitViewList;
    }

    public int insertFacility(Facility facility){
        int insertCnt = resourceManageMapper.insertFacility(facility);
        return insertCnt;
    }

    public int insertResourceType(ResourceType resourceType){
        int insertCnt = resourceManageMapper.insertResourceType(resourceType);
        return insertCnt;
    }

    public int insertResource(Resource resource){
        Integer seaSiteID = resource.getSeaSiteID();
        Integer resourceTypeID = resource.getResourceTypeID();

        int seaSiteCnt = resourceManageMapper.countSeaSite(seaSiteID);
        int resourceTypeCnt = resourceManageMapper.countResourceType(resourceTypeID);

        if(seaSiteCnt == 0 || resourceTypeCnt == 0){
            throw new ForeignKeyColumnMissException();
        }
        int insertCnt = resourceManageMapper.insertResource(resource);
        return insertCnt;
    }

    public int insertResourceExploitRecord(ResourceExploit resourceExploit){
        Integer resourceID = resourceExploit.getResourceID();
        Integer facilityID = resourceExploit.getFacilityID();

        int resourceCnt = resourceManageMapper.countResource(resourceID);
        int facilityCnt = resourceManageMapper.countFacility(facilityID);

        if(resourceCnt == 0 || facilityCnt == 0){
            throw new ForeignKeyColumnMissException();
        }
        int insertCnt = resourceManageMapper.insertResourceExploitRecord(resourceExploit);
        return insertCnt;
    }

    @Transactional
    public int delFacility(Integer facilityID){
        int delCnt = 0;
        List<Integer> resourceExploitRecordIDList =
                resourceManageMapper.listResourceExploitRecordID(facilityID,null);
        if(!resourceExploitRecordIDList.isEmpty())
            resourceManageMapper.delResourceExploitRecord(resourceExploitRecordIDList);
        delCnt = resourceManageMapper.delFacility(facilityID);
        return delCnt;
    }

    //如果资源类型ID被使用则不允许删除
    public int delResourceType(Integer resourceTypeID){
        int delCnt = 0;
        List<Integer> resourceIDList = resourceManageMapper.listResourceID(resourceTypeID);
        if(!resourceIDList.isEmpty())
            throw new ResourceTypeUsingException();
        delCnt = resourceManageMapper.delResourceType(resourceTypeID);
        return delCnt;
    }

    @Transactional
    public int delResource(Integer resourceID){
        int delCnt = 0;
        List<Integer> resourceExploitRecordIDList =
                resourceManageMapper.listResourceExploitRecordID(null,resourceID);
        if(!resourceExploitRecordIDList.isEmpty())
            resourceManageMapper.delResourceExploitRecord(resourceExploitRecordIDList);
        delCnt = resourceManageMapper.delResource(resourceID);
        return delCnt;
    }

    public int delResourceExploitRecord(List<Integer> resourceExploitRecordIDList){
        int delCnt = resourceManageMapper.delResourceExploitRecord(resourceExploitRecordIDList);
        return delCnt;
    }


    public List<SeaSite> getSeaSites(List<Integer> seaSiteIDList) {
        List<SeaSite> seaSiteList = resourceManageMapper.getSeaSites(seaSiteIDList);
        return seaSiteList;
    }

    public List<FacilityView> getFacilityViews() {
        List<FacilityView> facilityViewList = resourceManageMapper.getFacilityViews();
        return facilityViewList;
    }

    public int insertSeaSite(SeaSite seaSite) {
        return resourceManageMapper.insertSeaSite(seaSite);
    }
}
