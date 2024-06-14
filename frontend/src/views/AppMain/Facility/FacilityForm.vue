<template>
    <el-form ref="FacilityForm" :model="facilityItem" :rules="rules" label-width="100px">

        <el-form-item label="设备名称" prop="facilityName">
            <el-col :span="15">
                <el-input v-model="facilityItem.facilityName" maxlength="40" show-word-limit></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="地理位置" prop="seaSiteID">
            <el-col :span="10">
                <el-select v-model="facilityItem.seaSiteID" placeholder="请选择">
                    <el-option v-for="seaSiteItem in seaSites" :key="seaSiteItem.seaSiteID"
                        :label="seaSiteItem.seaSiteName" :value="seaSiteItem.seaSiteID">
                    </el-option>
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="地理位置经度">
            <el-col :span="10">
                <el-input :disabled="true" v-model="dispLat"></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="地理位置纬度">
            <el-col :span="10">
                <el-input :disabled="true" v-model="dispLon"></el-input>
            </el-col>
        </el-form-item>

        <el-form-item label="设备描述" prop="description">
            <el-col :span="15">
                <el-input type="textarea" v-model="facilityItem.description" maxlength="200" show-word-limit>
                </el-input>
            </el-col>
        </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios'


export default {
    name: "FacilityForm",
    computed: {
        dispLat() {
            if (this.facilityItem.seaSiteID !== null) {
                let seaSiteIndex = this.seaSites.findIndex(seaSiteItem => {
                    return seaSiteItem.seaSiteID === this.facilityItem.seaSiteID;
                })
                if (seaSiteIndex >= 0 && seaSiteIndex < this.seaSites.length) {
                    return this.seaSites[seaSiteIndex].lat
                }
            }
            return null
        },
        dispLon() {
            if (this.facilityItem.seaSiteID !== null) {
                let seaSiteIndex = this.seaSites.findIndex(seaSiteItem => {
                    return seaSiteItem.seaSiteID === this.facilityItem.seaSiteID;
                })
                if (seaSiteIndex >= 0 && seaSiteIndex < this.seaSites.length) {
                    return this.seaSites[seaSiteIndex].lon
                }
            }
            return null
        }
    },

    watch: {
        facilityItem: {
            handler(newValue) {
                this.$emit('update:addFacilityItem', newValue)
            },
            deep: true
        }
    },

    props: ['addFacilityItem'],

    data() {
        return {
            facilityItem: {
                facilityID: null,
                seaSiteID: null,
                facilityName: null,
                description: null
            },

            seaSites: null,

            rules: {
                facilityName: [
                    { required: true, message: '请输入设备名称', trigger: 'blur' },
                ],
                seaSiteID: [
                    { required: true, message: '请选择设备的位置', trigger: 'change' }
                ]
            }
        };
    },

    methods: {
        checkForm() {
            let checkResultWrapper = true
            this.$refs.FacilityForm.validate((checkResult) => {
                checkResultWrapper = checkResult
            })
            return checkResultWrapper
        },

        fetchSeaSites() {
            let requestURL = this.$store.state.prefixURL + '/query/allSeasite'
            axios.get(requestURL).then(
                (responseData) => {
                    this.seaSites = responseData.data.obj
                }
            ).catch(error =>{
                console.log(error)
                this.$message.error("网页请求失败!")
            })
        }
    }
}
</script>