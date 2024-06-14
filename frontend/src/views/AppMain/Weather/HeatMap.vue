<template>
    <el-card class="box-card" id="HeatMap">
        <div id="map-box-title" slot="header" class="clearfix">

            <span id="map-box-title-word">海洋温度热力图</span>

            <span id="map-box-header-picker">
                <el-date-picker v-model="queryDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
                </el-date-picker>
            </span>

            <span id="map-box-header-shower">
                <el-input v-model="dispNcFileName" :disabled="true" readonly style="width: 300px;">
                </el-input>
            </span>

            <span id="map-box-title-button2">
                <el-button type="primary" @click="handleNcDataQuery">生成热力图</el-button>
            </span>

            <span id="map-box-title-button1">
                <el-button type="primary" @click="handleNcFileQuery">查询数据集</el-button>
            </span>

        </div>

        <div id="mapBox">
            <baidu-map ref="myMap" class="bm-view" :center="center" :zoom="zoom"
            :min-zoom="zoom" :max-zoom="zoom">
                <bml-heatmap :data="sstData" :max="1" :radius="myRadius" :gradient="myGradient">
                </bml-heatmap>
            </baidu-map>
            <div id="zoomBar">
                <el-slider v-model="zoom" vertical height="500px" :min="4" :max="15" :disabled="true">
                </el-slider>
            </div>
        </div>
    </el-card>

</template>

<script>
import axios from 'axios'
import { BmlHeatmap } from 'vue-baidu-map'

export default {

    name: 'HeatMap',
    components: {
        BmlHeatmap
    },

    computed: {
        myRadius() {
            return this.zoom * 5
        },
        dispNcFileName() {
            if (!this.ncfile.ncFileName)
                return null
            return this.ncfile.ncFileName
        }
    },
    data() {
        return {
            center: { lng: 116.404, lat: 39.915 },
            zoom: 5,
            myGradient: {
                .0: 'rgb(0,   0, 255)', // 蓝色  
                // .1: 'rgb(0,  51, 221)',
                .2: 'rgb(0, 102, 177)',
                // .3: 'rgb(0, 153, 133)',
                // .4: 'rgb(51, 204,  85)', // 绿色  
                .5: 'rgb(102,255,  34)',
                // .6: 'rgb(153,255,   0)',
                // .7: 'rgb(204,204,   0)',
                .8: 'rgb(255,153,   0)', // 黄色  
                // .9: 'rgb(255, 102,  0)',
                1: 'rgb(255,   0,  0)'  // 红色
            },
            queryDate: null,
            ncfile: {
                ncFileID: null,
                ncFileName: null,
                startDate: null,
                endDate: null
            },
            sstData: null,
        };
    },
    methods: {
        async handleNcDataQuery() {
            let prefixURL = this.$store.state.prefixURL
            if (!this.ncfile.ncFileID) {
                this.$message.error("查询数据集不能为空!");
                return
            }

            let queryNcDataURL = prefixURL + '/obv/ncdata'

            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });

            try {
                const response = await axios.get(queryNcDataURL, {
                    params: {
                        ncFileID: this.ncfile.ncFileID
                    }
                });
                this.sstData = response.data.obj;
            } catch (error) {
                console.log(error);
                this.$message.error("网络请求失败!");
            } finally {
                loading.close()
            }
        },

        async handleNcFileQuery() {
            let prefixURL = this.$store.state.prefixURL

            let queryNcFileURL = prefixURL + '/obv/ncfile'
            if (!this.queryDate) {
                this.$message.error("查询日期不能为空!");
                return
            }


            try {
                const response = await axios.get(queryNcFileURL, {
                    params: {
                        queryDate: this.queryDate
                    }
                });
                this.ncfile = response.data.obj;
            } catch (error) {
                console.log(error);
                this.$message.error("网络请求失败!");
            }

        }

    },
    mounted() {

    }

};  
</script>


<style scoped>
#HeatMap {
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

#map-box-header-shower {
    float: left;
    margin-left: 10px;
}

#map-box-title-button1 {
    float: right;
    margin: 0px 55px 5px 0px;
}

#map-box-title-button2 {
    float: right;
    margin: 0px 10px 5px 0px;
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

