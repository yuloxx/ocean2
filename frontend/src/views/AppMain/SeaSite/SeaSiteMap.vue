<template>
    <el-card class="box-card" id="SeaSiteMap">
        <div id="map-box-title" slot="header" class="clearfix">

            <div id="map-box-title-word" style="float: left;">
                <span style="margin-right: 10px;">海洋位置地图</span>
                <el-button type="primary" size="small" @click="bmMarkerVisible = true">新 增</el-button>
                <el-button type="primary" size="small" @click="handleAddConfirm">确 定</el-button>
            </div>

            <el-button id="map-box-title-button" type="primary" @click="resetSeaSiteMark">重 置</el-button>
        </div>
        <div id="mapBox">
            <baidu-map ref="myMap" class="bm-view" :center="center" :zoom="zoom">
                <div v-if="resetMap">
                    <!-- 在这里可以添加其他地图覆盖物，如标记、信息窗口等  "-->
                    <bm-marker v-for="seaSiteItem in seaSiteList" :key="seaSiteItem.seaSiteID"
                        :position="{ lng: seaSiteItem.lon, lat: seaSiteItem.lat }" dragging="true">
                        <bm-label :content="seaSiteItem.seaSiteName" :offset="{ width: 0, height: -30 }" />
                    </bm-marker>

                    <bm-marker :position="{ lng: 116.404, lat: 39.915 }" v-if="bmMarkerVisible"
                        animation="BMAP_ANIMATION_BOUNCE" @dragend="handlePositionChanged" :dragging="true">
                    </bm-marker>
                </div>
            </baidu-map>
            <div id="zoomBar">
                <el-slider v-model="zoom" vertical height="500px" :min="4" :max="15">
                </el-slider>
            </div>
        </div>

        <el-dialog title="确认增加地点" :visible.sync="dialogVisible" width="30%">
            <el-descriptions title="海洋地点信息">
                <el-descriptions-item label="经度">{{ this.addSeaSiteItem.lon }}</el-descriptions-item>
                <el-descriptions-item label="纬度">{{ this.addSeaSiteItem.lat }}</el-descriptions-item>
            </el-descriptions>

            <el-input v-model="addSeaSiteItem.seaSiteName" placeholder="海洋地点名称"></el-input>

            <span slot="footer" class="dialog-footer">
                <el-button @click="handleCancle">取 消</el-button>
                <el-button type="primary" @click="handleConfirm">确 定</el-button>
            </span>
        </el-dialog>

    </el-card>

</template>

<script>
import axios from 'axios'
export default {

    name: 'SeaSiteMap',

    data() {
        return {
            center: { lng: 116.404, lat: 39.915 },
            zoom: 5,
            resetMap: true,

            seaSiteList: null,

            bmMarkerVisible: false,

            addSeaSiteItem: {
                seaSiteID: null,
                seaSiteName: null,
                lat: null,
                lon: null
            },

            dialogVisible: false
        };
    },
    methods: {
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

            ).catch(error =>{
                console.log(error)
                this.$message.error("网页请求失败")
            })
        },
        handleAddConfirm() {
            if(this.bmMarkerVisible)
                this.dialogVisible = true
        },
        handleConfirm() {
            let requestURL = this.$store.state.prefixURL + '/insert/seasite'
            axios.post(requestURL, this.addSeaSiteItem).then(
                (responseData) => {
                    this.seaSiteList = responseData.data.obj
                    this.fetchSeaSites()
                    this.dialogVisible = false
                    this.bmMarkerVisible = false
                }
            ).catch(error =>{
                console.log(error)
                this.$message.error("网页请求失败")
            })
        },
        handlePositionChanged(event) {
            this.addSeaSiteItem.lon = event.point.lng
            this.addSeaSiteItem.lat = event.point.lat
        },
        handleCancle() {
            this.bmMarkerVisible = false
            this.dialogVisible = false
        }
    },
    mounted() {
        this.fetchSeaSites()
    }

};  
</script>


<style scoped>
#SeaSiteMap {
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
