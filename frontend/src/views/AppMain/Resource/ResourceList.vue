<template>

    <el-card class="box-card" id="ResourceList">

        <!--表头-->
        <div slot="header" class="clearfix">
            <span>资源列表</span>
            <el-button style="float: right" type="primary" @click="handleAdd">增加资源</el-button>
            <span style="margin: 0px 10px 0px 10px;">
                <el-select v-model="queryResourceTypeID" placeholder="请选择资源类型查询条件" clearable>
                    <el-option v-for="resourceTypeItem in resourceTypeList" :key="resourceTypeItem.resourceTypeID"
                        :label="resourceTypeItem.resourceName" :value="resourceTypeItem.resourceTypeID">
                    </el-option>
                </el-select>
                <el-button type="primary" @click="fetchConditionedResourceView(queryResourceTypeID)"
                    style="margin-left: 10px;">查询</el-button>
            </span>
        </div>

        <!--数据列表-->
        <el-table id="table-body" height="600" border :data="listData">
            <el-table-column fixed prop="resourceID" label="资源编号" width="100">
            </el-table-column>

            <el-table-column prop="resourceName" label="资源名称" width="200">
            </el-table-column>

            <el-table-colum prop="unitName" label="单位" width="100">
            </el-table-colum>

            <el-table-column prop="lat" label="经度" width="150">
            </el-table-column>

            <el-table-column prop="lon" label="纬度" width="150">
            </el-table-column>

            <el-table-column prop="hasQuota" label="是否有配额" width="150">
                <template slot-scope="scope">
                    <span>{{ scope.row.hasQuota ? '是' : '否' }}</span>
                </template>
            </el-table-column>

            <el-table-column prop="quota" label="配额" width="150">
                <template slot-scope="scope">
                    <span>{{ scope.row.hasQuota ? scope.row.quota : '-' }}</span>
                </template>
            </el-table-column>

            <el-table-column fixed="right" label="操作" width="150">
                <template slot-scope="scope">
                    <el-button @click="handleDel(scope.row)" type="danger" size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--确认删除对话框-->
        <el-dialog title="提示" :visible.sync="delConfirmDialogVisible" width="30%">
            <strong>确认要删除选定的记录吗?删除资源将会连带删除与之关联的记录</strong>
            <p>ID:{{ delResourceItem.resourceID }}</p>
            <p>设备名称:{{ delResourceItem.resourceName }}</p>

            <span slot="footer" class="dialog-footer">
                <el-button @click="delConfirmDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleDelConfirm">确 定</el-button>
            </span>
        </el-dialog>

        <!--新增资源对话框-->
        <el-dialog title="增加资源" :visible.sync="addDialogVisible" width="50%">
            <ResourceForm ref="ResourceForm" :addResourceItem.sync="addResourceItem"></ResourceForm>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddResource">确 定</el-button>
            </span>
        </el-dialog>

    </el-card>

</template>

<script>
import ResourceForm from './ResourceForm.vue'
import axios from 'axios'

export default {
    name: "ResourceList",
    components: {
        ResourceForm: ResourceForm
    },
    methods: {
        handleDel(row) {
            this.delResourceItem.resourceName = row.resourceName
            this.delResourceItem.resourceID = row.resourceID
            this.delConfirmDialogVisible = true
        },
        handleDelConfirm() {
            let requestURL = this.$store.state.prefixURL + '/del/resource/'
            requestURL = requestURL + this.delResourceItem.resourceID
            axios.delete(requestURL).then((responce) => {
                console.log(responce)
                this.fetchConditionedResourceView(this.queryResourceTypeID)
                this.successMessage('删除资源操作成功')
                this.delResourceItem.resourceID = null
                this.delResourceItem.resourceName = null
                this.delConfirmDialogVisible = false
            })
        },
        handleAdd() {
            this.addDialogVisible = true
            this.$nextTick(() => {
                this.$refs.ResourceForm.fetchSeaSiteList()
                this.$refs.ResourceForm.fetchResourceTypeList()
            })
        },
        handleAddResource() {
            let checkResult = this.$refs.ResourceForm.checkForm()
            if (checkResult) {
                let requestURL = this.$store.state.prefixURL + '/insert/resource'
                axios.post(requestURL, this.addResourceItem).then(
                    (responce) => {
                        console.log(responce)
                        this.$message.success("新增资源操作成功")
                        this.fetchConditionedResourceView(this.queryResourceTypeID)
                        this.addDialogVisible = false

                        this.addResourceItem.resourceName = null
                        this.addResourceItem.unitName = null
                        this.addResourceItem.lat = null
                        this.addResourceItem.lon = null
                        this.addResourceItem.hasQuota = null
                        this.addResourceItem.quota = null
                    }
                ).catch(error => {
                    console.log(error)
                    this.$message.error("网页请求失败!")
                })
            }
        },
        fetchResourceView() {
            let requestURL = this.$store.state.prefixURL + '/query/resource'
            axios.get(requestURL).then(
                (responseData) => {
                    this.listData = responseData.data.obj
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
                    this.listData = response.data.obj
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
            )
        }
    },

    data() {
        return {
            delConfirmDialogVisible: false,
            addDialogVisible: false,

            delResourceItem: {
                resourceID: null,
                resourceName: null
            },

            addResourceItem: {
                resourceID: null,
                resourceTypeID: null,
                seaSiteID: null,
                quota: null,
            },
            listData: null,
            resourceTypeList: null,
            queryResourceTypeID: null,
        }
    },
    mounted() {
        this.fetchResourceView()
        this.fetchResourceTypeList()

    }
}
</script>

<style scoped>
#ResourceList {
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

#table-header-select {
    float: right;

}
</style>