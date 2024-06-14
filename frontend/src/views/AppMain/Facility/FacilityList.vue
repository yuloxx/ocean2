<template>

    <el-card class="box-card" id="FacilityList">

        <!--表头-->
        <div slot="header" class="clearfix">
            <span>设施列表</span>
            <el-button style="float: right" type="primary" @click="handleAdd">增加设施</el-button>
        </div>

        <!--数据列表-->
        <el-table id="table-body" height="600" border :data="listData">
            <el-table-column fixed prop="facilityID" label="设施编号" width="100">
            </el-table-column>
            <el-table-column prop="facilityName" label="设施名称" width="200">
            </el-table-column>
            <el-table-column prop="lat" label="经度" width="120">
            </el-table-column>
            <el-table-column prop="lon" label="纬度" width="120">
            </el-table-column>
            <el-table-column prop="description" label="设备描述" width="400">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="150">
                <template slot-scope="scope">
                    <el-button @click="handleDel(scope.row)" type="danger" size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--确认删除对话框-->
        <el-dialog title="提示" :visible.sync="delConfirmDialogVisible" width="30%">
            <strong>确认要删除选定的记录吗?删除设施将会连带删除与之关联的记录</strong>
            <p>设备ID:{{ delFacilityItem.facilityID }}</p>
            <p>设备名称:{{ delFacilityItem.facilityName }}</p>

            <span slot="footer" class="dialog-footer">
                <el-button @click="delConfirmDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleDelConfirm">确 定</el-button>
            </span>
        </el-dialog>

        <!--新增机构对话框-->
        <el-dialog title="增加设施" :visible.sync="addDialogVisible" width="50%">
            <FacilityForm ref="FacilityForm" :addFacilityItem.sync="addFacilityItem"></FacilityForm>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddFacility">确 定</el-button>
            </span>
        </el-dialog>

    </el-card>

</template>

<script>
import FacilityForm from './FacilityForm.vue'
import axios from 'axios'
export default {
    name: "facilityList",
    components: {
        FacilityForm: FacilityForm
    },
    methods: {
        handleDel(row) {
            this.delFacilityItem.facilityName = row.facilityName
            this.delFacilityItem.facilityID = row.facilityID
            this.delConfirmDialogVisible = true
        },
        handleDelConfirm() {
            let requestURL = this.$store.state.prefixURL + '/del/facility/'
            requestURL = requestURL + this.delFacilityItem.facilityID
            axios.delete(requestURL).then((responce) => {
                console.log(responce)
                this.fetchFacilityView()
                this.$message.success('删除海洋设备操作成功')
                this.delFacilityItem.facilityID = null
                this.delFacilityItem.facilityName = null
                this.delConfirmDialogVisible = false
            }).catch(error => {
                console.log(error)
                this.$message.error("网页请求失败!")
            })
        },
        handleAdd() {
            this.addDialogVisible = true
            this.$nextTick(() => {
                this.$refs.FacilityForm.fetchSeaSites()
            })
        },
        handleAddFacility() {
            let checkResult = this.$refs.FacilityForm.checkForm()
            if (checkResult) {
                let requestURL = this.$store.state.prefixURL + '/insert/facility'
                axios.post(requestURL, this.addFacilityItem).then(
                    (responce) => {
                        console.log(responce)
                        this.$message.success("新增设施操作成功")
                        this.fetchFacilityView()
                        this.addDialogVisible = false

                        this.addFacilityItem.facilityName = null
                        this.addFacilityItem.seasite_id = null
                        this.addFacilityItem.description = null
                    }
                ).catch(error => {
                    console.log(error)
                    this.$message.error("网页请求失败!")
                })
            }
        },
        fetchFacilityView() {
            let requestURL = this.$store.state.prefixURL + '/query/facilityView'
            axios.get(requestURL).then(
                (responseData) => {
                    this.listData = responseData.data.obj
                }
            ).catch(error =>{
                console.log(error)
                this.$message.error("网页请求失败!")
            })
        }
    },

    data() {
        return {
            delConfirmDialogVisible: false,
            addDialogVisible: false,

            delFacilityItem: {
                facilityID: null,
                facilityName: null
            },

            addFacilityItem: {
                facilityID: null,
                seasite_id: null,
                facilityName: null,
                description: null
            },
            listData: null
        }
    },
    mounted() {
        this.fetchFacilityView()
    }
}
</script>

<style>
#FacilityList {
    width: 1146px;
    height: 700px;
    overflow: hidden;

    text-align: left;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}


#table-body {
    /* height: 500px; */
    width: 100%;
    background-color: white;
}
</style>



<style>
.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box-card {
    width: 480px;
}
</style>