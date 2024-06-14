<template>
    <el-card class="box-card" id="StatisticMap">
        <div id="map-box-title" slot="header" class="clearfix">

            <span id="map-box-title-word">海洋温度统计图</span>

            <span id="map-box-header-picker">
                <el-date-picker v-model="myDateRange" start-placeholder="开始日期" end-placeholder="结束日期"
                    range-separator="至" type="daterange" value-format="yyyy-MM-dd" :unlink-panels="true">
                </el-date-picker>
            </span>

            <span id="map-box-title-button">
                <el-button type="primary" @click="handleSstQuery">查 询</el-button>
            </span>

        </div>

        <div id="mapBox">
            <baidu-map ref="myMap" class="bm-view" :center="center" :zoom="zoom">
                <bm-marker :position="queryPosition" animation="BMAP_ANIMATION_BOUNCE" :dragging="true"
                    @dragend="handlePositionChanged">
                </bm-marker>
            </baidu-map>
            <div id="zoomBar">
                <el-slider v-model="zoom" vertical height="500px" :min="4" :max="15">
                </el-slider>
            </div>
        </div>
        <el-drawer title="海洋温度统计数据" :visible.sync="drawerVisible" direction="btt"
        :size="drawerSize" @open="handleDrawerOpen" @close="handleDrawerClose">
            <StatisticData  ref="StatisticData" :sstValueList="sstValueList" :ncRangeList="ncRangeList" />
        </el-drawer>
    </el-card>

</template>

<script>
import axios from 'axios'
import StatisticData from './StatisticData.vue';
export default {

    name: 'StatisticMap',

    components: {
        StatisticData: StatisticData
    },

    data() {
        return {
            center: { lng: 116.404, lat: 39.915 },
            zoom: 5,
            myDateRange: null,
            queryPosition: { lng: 116.404, lat: 39.915 },

            sstValueList: null,
            ncRangeList: null,

            drawerVisible: false,
            drawerSize: '80%',
        };
    },
    methods: {
        handleDrawerOpen(){
            this.$nextTick(()=>{
                this.$refs.StatisticData.echartSetup(this.ncRangeList, this.sstValueList)
            })
        },

        handleDrawerClose(){
            this.$refs.StatisticData.echartSetup.echartDestroy()
        },
        handleSstQuery() {
            if (!this.myDateRange) {
                this.$message.error("日期参数不能为空!")
                return
            }
            let prefixURL = this.$store.state.prefixURL
            let requestURL = prefixURL + '/obv/ncStatistic'
            let requestParam = {
                startDate: this.myDateRange[0],
                endDate: this.myDateRange[1],
                lat: this.queryPosition.lat,
                lon: this.queryPosition.lng
            }
            //发起网络请求
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });

            axios.get(requestURL, {
                params: requestParam
            }).then(responseData => {
                this.sstValueList = responseData.data.obj.sstValueList
                this.ncRangeList = responseData.data.obj.ncRangeList
                this.drawerVisible = true
            }).catch(error => {
                console.log(error)
                this.$message.error("网络请求失败!")
            }).finally(()=>{
                loading.close();
            })
        },
        handlePositionChanged(event) {
            this.queryPosition.lat = event.point.lat
            this.queryPosition.lng = event.point.lng
        }
    },
    mounted() {

    }

};  
</script>


<style scoped>
#StatisticMap {
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

#map-box-title {
    width: 100%;
    height: 25px;

}

#map-box-title-word {
    float: left;
    padding-top: 8px;
}

#map-box-header-picker {
    float: left;
    margin-left: 10px;
}


#map-box-title-button {
    float: right;
    margin: 0px 55px 5px 0px;
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
