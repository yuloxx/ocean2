<template>
    <div id="AppSide">
        <el-menu 
            @select="handleSelectMethod" 
            style="text-align: left;"
            :default-openeds="['1','2','3','4','5']"
            >
            <!-------------首页--------------> 
            <el-submenu index="1">
                <template slot="title">系统首页</template>
                <el-menu-item index="1-1">系统简介</el-menu-item>
                <el-menu-item index="1-2">地点管理</el-menu-item>
            </el-submenu>

            <!-------------地理信息-------------->
            <el-submenu index="2">
                <template slot="title">地理信息</template>
                <el-menu-item index="2-1">气象信息</el-menu-item>
                <el-menu-item index="2-2">数据统计</el-menu-item>
                <el-menu-item index="2-3">热力图</el-menu-item>
            </el-submenu>


            <!-------------机构管理-------------->
            <el-submenu index="3">
                <template slot="title">设施管理</template>
                <el-menu-item index="3-1">设施列表</el-menu-item>
                <el-menu-item index="3-2">设施地图</el-menu-item>
            </el-submenu>


            <!-------------资源管理-------------->
            <el-submenu index="4">
                <template slot="title">资源管理</template>
                <el-menu-item index="4-1">资源类型</el-menu-item>
                <el-menu-item index="4-2">资源列表</el-menu-item>
                <el-menu-item index="4-3">资源地图</el-menu-item>
            </el-submenu>


            <!-------------开发记录-------------->
            <el-submenu index="5">
                <template slot="title">开发记录</template>
                <el-menu-item index="5-1">查询开发记录</el-menu-item>
                <el-menu-item index="5-2">新增开发记录</el-menu-item>
            </el-submenu>

        </el-menu>
    </div>
</template>


<script>
export default {
    methods: {
        handleSelectMethod(index) {
            const currRoutePath = this.$route.path
            if(index === ''){
                alert('6')
            }

            else if(index === '1-2' && currRoutePath !== '/seasite'){
                this.$router.replace({
                    name: 'SeaSiteMap'
                })
            }

            else if(index === '2-1' && currRoutePath !== '/forecast'){
                this.$router.replace({
                    name: 'ForecastMap'
                })
            }
            else if(index === '2-2' && currRoutePath !== '/sstStatistic'){
                this.$router.replace({
                    name: 'StatisticMap'
                })
            }
            else if(index === '2-3' && currRoutePath !== '/sstMap'){
                this.$router.replace({
                    name: 'SstMap'
                })
            }
            else if(index === '3-1' && currRoutePath !== '/facilityList'){
                // this.$router.push('/facilityList')

                this.$router.replace({
                    name: 'FacilityList'
                })
            }
            else if(index === '3-2' && currRoutePath !== '/facilityMap'){
                this.$router.replace({
                    name: 'FacilityMap'
                })
            }
            else if(index ==='4-1' && currRoutePath !== '/resourceType'){
                this.$router.replace({
                    name:'ResourceType'
                })
            }
            else if(index ==='4-2' && currRoutePath !== '/resource'){
                this.$router.replace({
                    name: 'ResourceList'
                })
            }
            else if(index ==='4-3' && currRoutePath !=='/resourceMap'){
                this.$router.replace({
                    name: 'ResourceMap'
                })
            }
            else if(index ===  '5-1' && currRoutePath !== '/resourceExploit'){
                this.$router.replace({
                    name: 'ResourceExploit'
                })
            }
            else if(index === '5-2' && currRoutePath !== '/resourceExploitAdd'){
                this.$router.replace({
                    name: 'ResourceExploitAdd'
                })
            }
        }
    },
}
</script>

<style>
#AppSide{
    margin: 0px;
    padding: 0px;
    border: 0px;
}
</style>