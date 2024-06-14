package com.example.ocean2.mapper;


import com.example.ocean2.pojo.other.Facility;
import com.example.ocean2.pojo.other.FacilityView;
import com.example.ocean2.pojo.other.SeaSite;
import com.example.ocean2.pojo.resource.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ResourceManageMapper {

    //查询“设备”接口，所有参数可以省略，传递null值即省略)
    List<Facility> getFacilities();

    //查询设备，包括设备经纬度
    List<FacilityView> getFacilityViews();

    List<ResourceType> getResourceType();
    List<SeaSite> getSeaSites(List<Integer> seaSiteIDList);

    //查询“资源”接口，所有参数可以省略，传递null值即省略)
    List<ResourceView> getResourceViews(Integer resourceTypeID);

    //查询“开发记录”接口，所有参数可以省略，传递null值即省略)
    List<ResourceExploitView> getResourceExploitViews(Integer resourceTypeID,
            Integer facilityID, LocalDate startDate,LocalDate endDate);

    List<Integer> listResourceExploitRecordID(Integer facilityID,Integer resourceID);

    List<Integer> listResourceID(Integer resourceTypeID);

    Integer countResourceType(Integer resourceTypeID);

    Integer countResource(Integer resourceID);

    Integer countSeaSite(Integer seaSiteID);

    Integer countFacility(Integer facilityID);

    int insertFacility(Facility facility);

    int insertResourceType(ResourceType resourceType);

    int insertResource(Resource resource);

    int insertResourceExploitRecord(ResourceExploit resourceExploit);

    //Dao层直接删除的接口，需要在服务层删除存在外键关联的表的数据
    int delFacility(Integer facilityID);

    //Dao层直接删除的接口，需要在服务层删除存在外键关联的表的数据
    int delResourceType(Integer resourceTypeID);

    //Dao层直接删除的接口，需要在服务层删除存在外键关联的表的数据
    int delResource(Integer resourceID);

    //Dao层直接删除的接口，需要在服务层删除存在外键关联的表的数据
    int delResourceExploitRecord(List<Integer> resourceExploitRecordIDList);


    int insertSeaSite(SeaSite seaSite);
}
