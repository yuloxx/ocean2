package com.example.ocean2.controller;


import com.example.ocean2.pojo.other.Facility;
import com.example.ocean2.pojo.other.FacilityView;
import com.example.ocean2.pojo.other.SeaSite;
import com.example.ocean2.pojo.resource.*;
import com.example.ocean2.pojo.web.BadRequestMessageEnum;
import com.example.ocean2.pojo.web.Result;
import com.example.ocean2.service.ResourceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResourceManageController {
    @Autowired
    ResourceManageService resourceManageService;

    @GetMapping("/query/facility")
    public Result getFacilities(){
        List<Facility> facilityList = resourceManageService.getFacilities();
        return Result.success(facilityList);
    }

    @GetMapping("/query/facilityView")
    public Result getFacilityViews(){
        List<FacilityView> facilityViewList = resourceManageService.getFacilityViews();
        return Result.success(facilityViewList);
    }


    @PostMapping("/query/seasite")
    public Result getSeaSites(@RequestBody List<Integer> seaSiteIDList){
        List<SeaSite> seaSiteList = resourceManageService.getSeaSites(seaSiteIDList);
        return Result.success(seaSiteList);
    }

    @GetMapping("/query/allSeasite")
    public Result getAllSeaSites(){
        List<SeaSite> seaSiteList = resourceManageService.getSeaSites(null);
        return Result.success(seaSiteList);
    }

    @GetMapping("/query/resourceType")
    public Result getResourceType(){
        List<ResourceType> resourceTypeList = resourceManageService.getResourceType();
        return Result.success(resourceTypeList);
    }

    @GetMapping("/query/resource")
    public Result getResourceViews(Integer resourceTypeID){
        List<ResourceView> resourceViewList = resourceManageService.getResourceViews(resourceTypeID);
        return Result.success(resourceViewList);
    }

    @GetMapping("/query/exploit")
    public Result getResourceExploitViews(Integer resourceTypeID, Integer facilityID,
                                          LocalDate startDate, LocalDate endDate)
    {
        if(resourceTypeID == null && facilityID == null){
            return Result.failure(400,
                    BadRequestMessageEnum.GET_PARAMETER_ILLEGAL_NULL.getMsg());
        }
        List<ResourceExploitView> resourceExploitViewList =
                resourceManageService.getResourceExploitViews(resourceTypeID,facilityID,startDate,endDate);
        return Result.success(resourceExploitViewList);
    }

    @PostMapping("/insert/seasite")
    public Result getSeaSites(@RequestBody SeaSite seaSite){
        resourceManageService.insertSeaSite(seaSite);
        return Result.success(seaSite.getSeaSiteID());
    }

    @PostMapping("/insert/facility")
    public Result insertFacility(@RequestBody Facility facility){
        if(facility.getSeaSiteID() == null || facility.getFacilityName() == null){
            return Result.failure(400,
                    BadRequestMessageEnum.POST_JSON_OBJECT_ILLEGAL.getMsg());
        }
       resourceManageService.insertFacility(facility);
        return Result.success(facility.getFacilityID());
    }

    @PostMapping("/insert/resourceType")
    public Result insertResourceType(@RequestBody ResourceType resourceType){
        if(resourceType.getResourceName() == null || resourceType.getHasQuota() == null){
            return Result.failure(400,
                    BadRequestMessageEnum.POST_JSON_OBJECT_ILLEGAL.getMsg());
        }
        resourceManageService.insertResourceType(resourceType);
        return Result.success(resourceType.getResourceTypeID());
    }

    @PostMapping("/insert/resource")
    public Result insertResource(@RequestBody Resource resource){
        if(resource.getResourceTypeID() == null || resource.getSeaSiteID() == null){
            return Result.failure(400,
                    BadRequestMessageEnum.POST_JSON_OBJECT_ILLEGAL.getMsg());
        }
        resourceManageService.insertResource(resource);
        return Result.success(resource.getResourceID());
    }

    @PostMapping("/insert/resourceExploit")
    public Result insertResourceType(@RequestBody ResourceExploit resourceExploit){
        if(resourceExploit.getFacilityID() == null ||
                resourceExploit.getResourceID() == null){
            return Result.failure(400,
                    BadRequestMessageEnum.POST_JSON_OBJECT_ILLEGAL.getMsg());
        }
        resourceManageService.insertResourceExploitRecord(resourceExploit);
        return Result.success(resourceExploit.getRecordID());
    }

    @DeleteMapping("/del/facility/{facilityID}")
    public Result delFacility(@PathVariable Integer facilityID){
        resourceManageService.delFacility(facilityID);
        return Result.success();
    }

    @DeleteMapping("/del/resourceType/{resourceTypeID}")
    public Result delResourceType(@PathVariable Integer resourceTypeID){
        resourceManageService.delResourceType(resourceTypeID);
        return Result.success();
    }

    @DeleteMapping("/del/resource/{resourceID}")
    public Result delResource(@PathVariable Integer resourceID){
        resourceManageService.delResource(resourceID);
        return Result.success();
    }

    @DeleteMapping("/del/resourceExploit/{resourceExploitID}")
    public Result delResourceExploitRecord(@PathVariable Integer resourceExploitID){
        List<Integer> IDlist = new ArrayList<>();
        IDlist.add(resourceExploitID);
        resourceManageService.delResourceExploitRecord(IDlist);
        return Result.success();
    }
}
