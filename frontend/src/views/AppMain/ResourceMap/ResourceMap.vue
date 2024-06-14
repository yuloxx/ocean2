<template>
    <el-card class="box-card" id="ResourceMap">
        <div id="map-box-title" slot="header" class="clearfix">

            <div id="map-box-title-word">资源分布地图</div>

            <span style="margin-left: 10px; float:left">
                <el-select v-model="queryResourceTypeID" placeholder="请选择资源类型查询条件" clearable>
                    <el-option v-for="resourceTypeItem in resourceTypeList" :key="resourceTypeItem.resourceTypeID"
                        :label="resourceTypeItem.resourceName" :value="resourceTypeItem.resourceTypeID">
                    </el-option>
                </el-select>
                <el-button type="primary" @click="fetchConditionedResourceView(queryResourceTypeID)"
                    style="margin-left: 10px;">查询</el-button>
            </span>

            <el-button id="map-box-title-button" type="primary" @click="resetResourceMark">重置</el-button>
        </div>

        <div id="mapBox">
            <baidu-map ref="myMap" class="bm-view" :center="center" :zoom="zoom">
                <div v-if="resetMap">
                    <!-- 在这里可以添加其他地图覆盖物，如标记、信息窗口等  "-->
                    <bm-marker v-for="resourceItem in resourceList" :key="resourceItem.resourceID"
                        :position="{ lng: resourceItem.lon, lat: resourceItem.lat }" dragging="true"
                        @click="handleMarkerClick(resourceItem)">
                        <bm-label :content="resourceItem.resourceName" :offset="{ width: 0, height: -30 }" />
                    </bm-marker>
                </div>
            </baidu-map>
            <div id="zoomBar">
                <el-slider v-model="zoom" vertical height="500px" :min="4" :max="15">
                </el-slider>
            </div>
        </div>

        <ResourceMsgDialog ref="ResourceMsgDialog" :resourceItem="selectResourceItem" />
    </el-card>

</template>

<script>
import ResourceMsgDialog from './ResourceMsgDialog.vue'
import axios from 'axios'

export default {
    name: 'ResourceMap',
    components: {
        ResourceMsgDialog: ResourceMsgDialog
    },
    data() {
        return {
            center: { lng: 116.404, lat: 39.915 },
            zoom: 5,
            resetMap: true,

            resourceList: null,
            resourceTypeList: null,
            queryResourceTypeID: null,

            selectResourceItem: null
        };
    },
    methods: {
        resetResourceMark() {
            this.resetMap = false
            setTimeout(() => {
                this.resetMap = true;
            }, 10)
        },
        handleMarkerClick(resourceItemParam) {
            this.selectResourceItem = resourceItemParam
            this.$refs.ResourceMsgDialog.showDialog()

        },
        fetchResourceView() {
            let requestURL = this.$store.state.prefixURL + '/query/resource'
            axios.get(requestURL).then(
                (responseData) => {
                    this.resourceList = responseData.data.obj
                }
            ).catch(error => {
                console.log(error)
                this.$message.error("网页请求失败!")
            })
        },
        fetchConditionedResourceView(queryResourceTypeIDParam) {
            if (queryResourceTypeIDParam !== null) {
                let requestURL = this.$store.state.prefixURL + '/query/resource'
                axios.get(requestURL, {
                    params: {
                        resourceTypeID: queryResourceTypeIDParam
                    }
                }).then(response => {
                    this.resourceList = response.data.obj
                }).catch(error => {
                    console.log(error)
                    this.$message.error("网页请求失败!")
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
            ).catch(error =>{
                console.log(error)
                this.$message.error("网页请求失败!")
            })
        },
    },
    mounted() {
        this.fetchResourceTypeList()
    },

};  
</script>


<style scoped>
#ResourceMap {
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
