<template>
    <!-- <el-descriptions class="margin-top" title="带边框列表" :column="3" :size="size" border> -->
    <el-dialog title="资源信息" :visible.sync="dialogVisible" width="50%" ref="ResourceMsgDialog">

        <el-descriptions class="margin-top" :column="2" border id="ResourceDescription">

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label" >
                    资源编号
                </template>
                {{ resourceItem.resourceID }}
            </el-descriptions-item>

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label">
                    资源名称
                </template>
                {{ resourceItem.resourceName }}
            </el-descriptions-item>

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label">
                    资源单位
                </template>
                {{ resourceItem.unitName !== null ? resourceItem.unitName : '-' }}
            </el-descriptions-item>

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label">
                    资源位置经度
                </template>
                {{ resourceItem.lat }}
            </el-descriptions-item>

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label">
                    资源位置纬度
                </template>
                {{ resourceItem.lon }}
            </el-descriptions-item>

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label">
                    是否有配额
                </template>
                {{ resourceItem.hasQuota ? '是' : '否' }}
            </el-descriptions-item>

            <el-descriptions-item labelStyle="width:20%">
                <template slot="label">
                    资源配额
                </template>
                {{ resourceItem.hasQuota ? resourceItem.quota : '-' }}
            </el-descriptions-item>

        </el-descriptions>

        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
    </el-dialog>
</template>



<script>
export default {
    name: 'ResourceMsgDialog',

    props: ['resourceItem'],

    data() {
        return {
            dialogVisible: false
        };
    },

    methods: {
        showDialog() {
            console.log('showDialogCalled')
            this.dialogVisible = true
        }
    },
}
</script>
