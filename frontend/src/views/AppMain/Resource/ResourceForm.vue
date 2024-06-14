<template>
    <el-form ref="ResourceForm" :model="resourceItem" :rules="rules" label-width="100px">

        <el-form-item label="资源类型" prop="resourceTypeID">
            <el-col :span="10">
                <el-select v-model="resourceItem.resourceTypeID" placeholder="请选择">
                    <el-option v-for="resourceTypeItem in resourceTypeList" :key="resourceTypeItem.resourceTypeID"
                        :label="resourceTypeItem.resourceName" :value="resourceTypeItem.resourceTypeID">
                    </el-option>
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="单位">
            <el-col :span="10">
                <el-input :disabled="true" :value="dispUnitName" readonly></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="是否有配额">
            <el-col :span="10">
                <el-input :disabled="true" :value="dispHasQuota !== null ? dispHasQuota ? '是' : '否' : '-'"></el-input>
            </el-col>
        </el-form-item>


        <el-form-item label="地理位置" prop="seaSiteID">
            <el-col :span="10">
                <el-select v-model="resourceItem.seaSiteID" placeholder="请选择">
                    <el-option v-for="seaSiteItem in seaSiteList" :key="seaSiteItem.seaSiteID"
                        :label="seaSiteItem.seaSiteName" :value="seaSiteItem.seaSiteID">
                    </el-option>
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="地理位置经度">
            <el-col :span="10">
                <el-input :disabled="true" :value="dispLat" readonly></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="地理位置纬度">
            <el-col :span="10">
                <el-input :disabled="true" :value="dispLon" readonly></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="配额" prop="quota">
            <el-col :span="10">
                <el-input :disabled="dispHasQuota !== null ? !dispHasQuota : true" v-model="resourceItem.quota">
                </el-input>
            </el-col>
        </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios'

export default {
    name: "ResourceForm",

    watch: {
        resourceItem: {
            handler(newValue) {
                this.$emit('update:addResourceItem', newValue)
            },
            deep: true
        },
        dispHasQuota: {
            handler(newValue) {
                if (!(newValue === true)) {
                    this.resourceItem.quota = null
                }
            }
        }
    },

    props: ['addResourceItem'],

    methods: {
        checkForm() {
            let checkResultWrapper = true
            this.$refs.ResourceForm.validate((checkResult) => {
                checkResultWrapper = checkResult
            })
            return checkResultWrapper
        },

        fecthDatas() {
            this.fetchSeaSiteList()
            this.fetchResourceTypeList()
        },

        fetchSeaSiteList() {
            let requestURL = this.$store.state.prefixURL + '/query/allSeasite'
            axios.get(requestURL).then(
                (responseData) => {
                    this.seaSiteList = responseData.data.obj
                }
            )
        },
        fetchResourceTypeList() {
            let requestURL = this.$store.state.prefixURL + '/query/resourceType'
            axios.get(requestURL).then(
                (responseData) => {
                    this.resourceTypeList = responseData.data.obj

                }
            )
        },
        validateFloat(rule, value, callback) {
            if (this.dispHasQuota === null)
                return callback(new Error('未知配额规则'))

            if (this.dispHasQuota) {
                let reg = /^[0-9]*\.?[0-9]+$/
                if (reg.test(value)) {
                    callback()
                } else {
                    callback(new Error('请输入正确的浮点数'));
                }
            }
            else {
                if(value === null)
                    return callback()
                else
                    return callback(new Error('无配额但配额值非空'))
            }
        }
    },
    computed: {
        dispSeaSite() {
            if (this.resourceItem.seaSiteID !== null) {
                let seaSiteIndex = this.seaSiteList.findIndex(seaSiteItem => {
                    return seaSiteItem.seaSiteID === this.resourceItem.seaSiteID;
                })
                if (seaSiteIndex >= 0 && seaSiteIndex < this.seaSiteList.length) {
                    return this.seaSiteList[seaSiteIndex]
                }
            }
            return null
        },

        dispLat() {
            if (this.dispSeaSite !== null)
                return this.dispSeaSite.lat
            return null
        },

        dispLon() {
            if (this.dispSeaSite !== null)
                return this.dispSeaSite.lon
            return null
        },

        dispResourceType() {
            if (this.resourceItem.resourceTypeID !== null) {
                let resourceTypeIndex = this.resourceTypeList.findIndex(resourceTypeItem => {
                    return resourceTypeItem.resourceTypeID === this.resourceItem.resourceTypeID
                })
                if (resourceTypeIndex >= 0 && resourceTypeIndex < this.resourceTypeList.length) {
                    return this.resourceTypeList[resourceTypeIndex]
                }
            }
            return null
        },

        dispResourceName() {
            if (this.dispResourceType !== null)
                return this.dispResourceType.resourceName
            return null
        },

        dispUnitName() {
            if (this.dispResourceType !== null)
                return this.dispResourceType.unitName
            return null
        },

        dispHasQuota() {
            if (this.dispResourceType !== null)
                return this.dispResourceType.hasQuota
            return null
        },

        rules() {
            return {
                resourceTypeID: [
                    { required: true, message: '请选择资源的类型', trigger: 'change' }
                ],
                seaSiteID: [
                    { required: true, message: '请选择资源的位置', trigger: 'change' }
                ],
                quota: [
                    {
                        required: this.dispHasQuota === null ? false : this.dispHasQuota,
                        message: '请输入资源的配额', trigger: 'blur'
                    },
                    { validator: this.validateFloat, trigger: 'blur' },
                ]
            }
        }
    },
    data() {
        return {
            resourceItem: {
                resourceID: null,
                resourceTypeID: null,
                seaSiteID: null,
                quota: null
            },

            seaSiteList: null,
            resourceTypeList: null,
        };
    },
}
</script>