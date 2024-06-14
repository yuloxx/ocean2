import Vue from "vue";
import VueRouter from "vue-router";
import FacilityList from "@/views/AppMain/Facility/FacilityList.vue";
import FacilityMap from "@/views/AppMain/FacilityMap/FacilityMap.vue";
import ResourceTypeList from "@/views/AppMain/ResourceType/ResourceTypeList.vue"
import ResourceList from "@/views/AppMain/Resource/ResourceList.vue"
import ResourceMap from "@/views/AppMain/ResourceMap/ResourceMap.vue";
import ResourceExploitList from "@/views/AppMain/ResourceExploit/ResourceExploitList.vue"
import ResourceExploitAdd from "@/views/AppMain/ResourceExploit/ResourceExploitAdd.vue"
import SeaSiteMap from "@/views/AppMain/SeaSite/SeaSiteMap.vue";
import StatisticMap from "@/views/AppMain/Weather/StatisticMap.vue"
import ForecastMap from "@/views/AppMain/Weather/ForecastMap.vue"
import HeatMap from "@/views/AppMain/Weather/HeatMap.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "HomePage",
  },

  { path: "/facilityList", name: "FacilityList", component: FacilityList},
  { path: "/facilityMap", name: "FacilityMap", component: FacilityMap},
  { path: "/resourceType", name: "ResourceType", component: ResourceTypeList},
  { path: "/resource", name: "ResourceList", component: ResourceList},
  { path: "/resourceMap", name: "ResourceMap", component: ResourceMap},
  { path: "/resourceExploit", name: "ResourceExploit", component: ResourceExploitList},
  { path: "/resourceExploitAdd", name: "ResourceExploitAdd", component: ResourceExploitAdd},
  { path: "/seasite", name:"SeaSiteMap", component: SeaSiteMap},
  { path: "/sstStatistic", name:"StatisticMap", component: StatisticMap},
  { path: "/forecast", name: "ForecastMap", component: ForecastMap},
  { path: "/sstMap", name:"SstMap", component: HeatMap}
];

const router = new VueRouter({
  routes,
});

export default router;
