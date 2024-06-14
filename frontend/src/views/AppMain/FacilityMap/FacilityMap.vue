<template>
    <el-card class="box-card" id="FacilityMap">
        <div id="map-box-title" slot="header" class="clearfix">
            <div id="map-box-title-word">资源设施地图</div>
            <el-button id="map-box-title-button" type="primary" @click="resetFacilityMark">重置</el-button>
        </div>
        <div id="mapBox">
            <baidu-map ref="myMap" class="bm-view" :center="center" :zoom="zoom">
                <div v-if="resetMap">
                    <!-- 在这里可以添加其他地图覆盖物，如标记、信息窗口等  "-->
                    <bm-marker v-for="facilityItem in facilityList" :key="facilityItem.facilityID"
                        :position="{ lng: facilityItem.lon, lat: facilityItem.lat }" dragging="true">
                        <bm-label :content="facilityItem.facilityName" :offset="{ width: 0, height: -30 }" />
                    </bm-marker>
                </div>
            </baidu-map>
            <div id="zoomBar">
                <el-slider v-model="zoom" vertical height="500px" :min="4" :max="15">
                </el-slider>
            </div>
        </div>
        
    </el-card>

</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            center: { lng: 116.404, lat: 39.915 },
            zoom: 5,
            facilityList: null,
            resetMap: true
        };
    },
    methods: {
        resetFacilityMark() {
            this.resetMap = false
            setTimeout(() => {
                this.resetMap = true;
            }, 10)
        },
        fetchResourceView() {
            let requestURL = this.$store.state.prefixURL + '/query/resource'
            axios.get(requestURL).then(
                (responseData) => {
                    this.listData = responseData.data.obj
                }
            )
        },
        fetchConditionedResourceView(queryResourceTypeIDParam) {
            if (queryResourceTypeIDParam !== null) {
                let requestURL = this.$store.state.prefixURL + '/query/resource'
                axios.get(requestURL, {
                    params: {
                        resourceTypeID: queryResourceTypeIDParam
                    }
                }).then(response => {
                    this.listData = response.data.obj
                }).catch(error => {
                    console.log(error)
                })
            }
            else {
                this.fetchResourceView()
            }
        },
        fetchResourceTypeList() {
            let requestURL = this.$store.state.prefixURL + '/query/resourceType'
            axios.get(requestURL).then(
                (responseData) => {
                    this.resourceTypeList = responseData.data.obj
                }
            )
        },
    },
    mounted() {
        let requestURL = this.$store.state.prefixURL + '/query/facilityView'
        axios.get(requestURL).then(
            (responseData) => {
                this.facilityList = responseData.data.obj;
            }
        )
    }

};  
</script>


<style scoped>
#FacilityMap {
    width: 1146px;
    height: 700px;
    overflow: hidden;

    text-align: left;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}

#mapBox {
    width: 100%;
    height: 600px;
    margin: 0px;
    padding: 0px;
    border: 0px;
}

#map-box-title{
    width: 100%;
    height: 25px;

}
#map-box-title-word{
    float: left;
    padding-top: 8px;
}

#map-box-title-button{
    float: right;
    margin: 0px 55px 0px 0px;
}

#zoomBar {
    width: 5%;
    height: 600px;
    margin: 0px;
    padding: 0px;
    border: 0px;
    float: right;
}
.bm-view {
    width: 95%;
    height: 600px;
    /* 根据需要设置地图容器的高度 */
    float: left
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}
</style>



