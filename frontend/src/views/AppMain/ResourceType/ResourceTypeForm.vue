<template>
    <el-form ref="ResourceTypeForm" :model="resourceTypeItem" :rules="rules" label-width="100px">

        <el-form-item label="资源名称" prop="resourceName">
            <el-col :span="15">
                <el-input v-model="resourceTypeItem.resourceName" maxlength="50" show-word-limit></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="单位名称" prop="unitName">
            <el-col :span="15">
                <el-input v-model="resourceTypeItem.unitName" maxlength="20" show-word-limit></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="是否有配额" prop="hasQuota">
            <template>
                <el-radio v-model="resourceTypeItem.hasQuota" label="true">是</el-radio>
                <el-radio v-model="resourceTypeItem.hasQuota" label="false">否</el-radio>
            </template>
        </el-form-item>

    </el-form>
</template>

<script>


export default {
    name: "ResourceTypeForm",
    watch: {
        resourceTypeItem: {
            handler(newValue) {
                this.$emit('update:addResourceTypeItem', newValue)
            },
            deep: true
        }
    },

    props: ['addResourceTypeItem'],

    data() {
        return {
            resourceTypeItem: {
                resourceTypeID: null,
                resourceName: null,
                unitName: null,
                hasQuota: null
            },

            rules: {
                resourceName: [
                    { required: true, message: '请输入资源名称', trigger: 'blur' },
                ],
                hasQuota: [
                    { required: true, message: '请设置资源配额政策', trigger: 'change' }
                ]
            }
        };
    },

    methods: {
        checkForm() {
            let checkResultWrapper = true
            this.$refs.ResourceTypeForm.validate((checkResult) => {
                checkResultWrapper = checkResult
            })
            return checkResultWrapper
        }
    }
}
</script>