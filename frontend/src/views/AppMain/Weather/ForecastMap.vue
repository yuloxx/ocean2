<template>
    <el-card class="box-card" id="ForecastMap">
        <div id="map-box-title" slot="header" class="clearfix">
            <span style="margin-right: 10px;">海洋位置地图</span>
            <el-button id="map-box-title-button" type="primary" @click="resetSeaSiteMark">重 置</el-button>
        </div>

        <div id="mapBox">
            <baidu-map ref="myMap" class="bm-view" :center="center" :zoom="zoom">
                <div v-if="resetMap">
                    <!-- 在这里可以添加其他地图覆盖物，如标记、信息窗口等  "-->
                    <bm-marker v-for="seaSiteItem in seaSiteList" :key="seaSiteItem.seaSiteID"
                        :position="{ lng: seaSiteItem.lon, lat: seaSiteItem.lat }" dragging="true"
                        @click="HandleForecastQuery(seaSiteItem)">
                        <bm-label :content="seaSiteItem.seaSiteName" :offset="{ width: 0, height: -30 }" />
                    </bm-marker>
                </div>
            </baidu-map>
            <div id="zoomBar">
                <el-slider v-model="zoom" vertical height="500px" :min="4" :max="15">
                </el-slider>
            </div>
        </div>

        <el-dialog title="天气预报信息" :visible.sync="dialogVisible" width="50%">
            <div class="dialog-body">
                <el-descriptions title="海洋地点信息">
                    <el-descriptions-item label="名称">{{ selectSeaSite.seaSiteName }} </el-descriptions-item>
                    <el-descriptions-item label="经度">{{ selectSeaSite.lat }}</el-descriptions-item>
                    <el-descriptions-item label="纬度">{{ selectSeaSite.lon }}</el-descriptions-item>
                </el-descriptions>

                <el-descriptions title="天气信息">
                    <el-descriptions-item label="天气">{{ weatherDescription }}</el-descriptions-item>
                </el-descriptions>

                <el-descriptions title="温度信息">

                    <el-descriptions-item label="最高气温">
                        <DataFixer :floatValue="weatherTemperatureData.daymax"/>
                    </el-descriptions-item>

                    <el-descriptions-item label="最低气温">
                        <DataFixer :floatValue="weatherTemperatureData.daymin"/>
                    </el-descriptions-item>

                    <el-descriptions-item label="当前气温">
                        <DataFixer :floatValue="weatherTemperatureData.data"/>
                    </el-descriptions-item>

                </el-descriptions>

                <el-descriptions title="风速信息">
                    <el-descriptions-item label="最高风速">
                        <DataFixer :floatValue="weatherWindData.daymax"/>
                    </el-descriptions-item>

                    <el-descriptions-item label="最低风速">
                        <DataFixer :floatValue="weatherWindData.daymin"/>
                    </el-descriptions-item>

                    <el-descriptions-item label="当前风速">
                        <DataFixer :floatValue="weatherWindData.data"/>
                    </el-descriptions-item>
                </el-descriptions>

                <el-descriptions title="能见度信息">
                    <el-descriptions-item label="最高能见度">
                        <DataFixer :floatValue="weatherVisibilityData.daymax"/>
                    </el-descriptions-item>

                    <el-descriptions-item label="最低能见度">
                        <DataFixer :floatValue="weatherVisibilityData.daymin"/>
                    </el-descriptions-item>

                    <el-descriptions-item label="当前能见度">
                        <DataFixer :floatValue="weatherVisibilityData.data"/>
                    </el-descriptions-item>
                    
                </el-descriptions>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>

    </el-card>

</template>

<script>
import axios from 'axios'
import DataFixer from './ForecastDataFixer.vue'
export default {

    name: 'ForecastMap',
    components:{
        DataFixer: DataFixer
    },
    data() {
        return {
            center: { lng: 116.404, lat: 39.915 },
            zoom: 5,
            resetMap: true,

            seaSiteList: null,
            bmMarkerVisible: false,

            dialogVisible: false,

            selectSeaSite: {
                seaSiteID: null,
                seaSiteName: null,
                lat: null,
                lon: null
            },

            weatherTemperatureData: {
                daymax: null,
                daymin: null,
                data: null
            },
            weatherVisibilityData: {
                daymax: null,
                daymin: null,
                data: null
            },
            weatherWindData: {
                daymax: null,
                daymin: null,
                data: null
            },

            weatherDescription: null,
        };
    },
    methods: {
        toFixNumber(floatNumber){
            return floatNumber.toFixed(2)
        },
        resetSeaSiteMark() {
            this.resetMap = false
            setTimeout(() => {
                this.resetMap = true;
            }, 10)
        },
        fetchSeaSites() {
            let requestURL = this.$store.state.prefixURL + '/query/allSeasite'
            axios.get(requestURL).then(
                (responseData) => {
                    this.seaSiteList = responseData.data.obj
                }

            ).catch(error => {
                console.log(error)
                this.$message.error("网页请求失败")
            })
        },
        HandleForecastQuery(seaSiteItem) {
            let weatherServiceURL = this.$store.state.weatherServiceURL
            let weatherServiceToken = this.$store.state.weatherServiceToken
            let pointString = seaSiteItem.lon + ',' + seaSiteItem.lat

            this.selectSeaSite = seaSiteItem

            axios.get(weatherServiceURL, {
                params: {
                    token: weatherServiceToken,
                    point: pointString
                }
            }).then(responseData => {
                let pureData = responseData.data.data
                if (responseData.data.code === 200) {
                    this.weatherDescription = pureData.calculationPM[0].pm
                    this.weatherTemperatureData = pureData["2T_instant"]
                    this.weatherVisibilityData = pureData.VIS_instant
                    this.weatherWindData = pureData.UV_instant

                    this.dialogVisible = true
                }
                else {
                    console.log(responseData.data)
                    this.$message.error("气象预报服务请求失败")
                }

            }).catch(error => {
                console.log(error)
                this.$message.error("网络请求失败")
            })
        }
    },
    mounted() {
        this.fetchSeaSites()
    }

};  
</script>


<style scoped>
#ForecastMap {
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


#map-box-title-button {
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
