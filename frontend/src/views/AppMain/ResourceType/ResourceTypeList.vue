<template>

    <el-card class="box-card" id="ResourceTypeList">

        <!--表头-->
        <div slot="header" class="clearfix">
            <div style="float: left; padding-top: 8px;">资源类型列表</div>
            <el-button style="float: right" type="primary" @click="handleAdd">增加资源类型</el-button>
        </div>

        <!--数据列表-->
        <el-table id="table-body" height="600" border :data="listData">
            <el-table-column fixed prop="resourceTypeID" label="资源类型编号" width="200">
            </el-table-column>
            <el-table-column prop="resourceName" label="资源名称" width="300">
            </el-table-column>
            <el-table-column prop="unitName" label="单位" width="200">
            </el-table-column>
            <el-table-column prop="hasQuota" label="是否有配额" width="150">
                <template slot-scope="scope">
                    <span>{{ scope.row.hasQuota ? '是' : '否' }}</span>
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
            <strong>确认要删除选定的记录吗?删除设施将会连带删除与之关联的记录</strong>
            <p>资源类型ID:{{ delResourceTypeItem.resourceTypeID }}</p>
            <p>资源名称:{{ delResourceTypeItem.resourceName }}</p>

            <span slot="footer" class="dialog-footer">
                <el-button @click="delConfirmDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleDelConfirm">确 定</el-button>
            </span>
        </el-dialog>

        <!--新增资源类型对话框-->
        <el-dialog title="增加资源类型" :visible.sync="addDialogVisible" width="50%">
            <ResourceTypeForm ref="ResourceTypeForm" :addResourceTypeItem.sync="addResourceTypeItem" />
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddResourceType">确 定</el-button>
            </span>
        </el-dialog>

    </el-card>

</template>

<script>
import ResourceTypeForm from './ResourceTypeForm.vue'
import axios from 'axios'
export default {
    name: "ResourceTypeList",
    components: {
        ResourceTypeForm: ResourceTypeForm
    },
    methods: {
        handleDel(row) {
            this.delResourceTypeItem.resourceName = row.resourceName
            this.delResourceTypeItem.resourceTypeID = row.resourceTypeID
            this.delConfirmDialogVisible = true
        },
        handleDelConfirm() {
            let requestURL = this.$store.state.prefixURL + '/del/resourceType/'
            requestURL = requestURL + this.delResourceTypeItem.resourceTypeID
            axios.delete(requestURL).then((response) => {
                console.log(response)
                this.fetchResourceTypeList()
                this.delResourceTypeItem.resourceTypeID = null
                this.delResourceTypeItem.resourceName = null
                this.delConfirmDialogVisible = false
                if (response.data.code === 0)
                    this.$message.success('删除资源类型操作成功')
                else
                    this.$message.error('删除失败，资源类型正在被使用')
            })
        },
        handleAdd() {
            this.addDialogVisible = true
        },
        handleAddResourceType() {
            let checkResult = this.$refs.ResourceTypeForm.checkForm()
            if (checkResult) {
                let requestURL = this.$store.state.prefixURL + '/insert/resourceType'
                axios.post(requestURL, this.addResourceTypeItem).then(
                    (responce) => {
                        console.log(responce)
                        this.$message.success("新增资源类型操作成功")
                        this.fetchResourceTypeList()
                        this.addDialogVisible = false

                        this.addResourceTypeItem.resourceName = null
                        this.addResourceTypeItem.unitName = null
                        this.addResourceTypeItem.hasQuota = null
                    }
                ).catch(error => {
                    console.log(error)
                    this.$message.error("网页请求失败!")
                })
            }
        },
        fetchResourceTypeList() {
            let requestURL = this.$store.state.prefixURL + '/query/resourceType'
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

            delResourceTypeItem: {
                resourceTypeID: null,
                resourceName: null
            },

            addResourceTypeItem: {
                resourceTypeID: null,
                resourceName: null,
                unitName: null,
                hasQuota: null
            },
            listData: null
        }
    },
    mounted() {
        this.fetchResourceTypeList()
    }
}
</script>

<style scoped>
#ResourceTypeList {
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



<style scoped>
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