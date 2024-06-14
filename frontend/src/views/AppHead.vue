<template>
    <div id="AppHead">

        <div id="head-title-logo">

        </div>

        <div id="head-title-word">
            海洋资源管理系统
        </div>

        <!-- 右侧浮动元素，倒序排列-->
        <div id="head-link-about">
            <el-link type="primary" :href="aboutLinkUrl" :underline="false">
                <i class="el-icon-link"></i>
                关于 
            </el-link>
        </div>


        <div id="head-link-oceanData">
            <el-link type="primary" :href="oceanDataLinkUrl" :underline="false">
                <i class="el-icon-s-data"></i>
                海洋数据
            </el-link>
        </div>


        <div id="head-currTime">
            <i class="el-icon-time"></i>
            {{ currTime }}
        </div>


    </div>
</template>


<script>
import dayjs from 'dayjs'
export default {
    data(){
        return{
            currTime:null,
            intervalID:null,
            aboutLinkUrl:"https://bilibili.com",
            oceanDataLinkUrl:"https://baidu.com"
        }
    },
    methods:{
        updateTime(){
            this.currTime = dayjs().format('YYYY-MM-DD HH:mm:ss')
        }

    },
    mounted() {
        this.currTime = dayjs().format('YYYY-MM-DD HH:mm:ss')
        this.intervalID = setInterval(this.updateTime,1000)
    },
    beforeDestroy() {
        clearInterval(this.intervalID);  
    },
}
</script>

<style>
#AppHead{
    height: 100px;
    margin: 0px;
    padding: 0px;
    border: 0px;
    overflow: hidden;
}

#AppHead::after{
    content: "";  
    display: block;  
    clear: both; 
}

#head-title-logo{
    float: left;
    height: 84px;
    width: 84px;
    margin: 8px 2px;
    padding: 0px;
    border: 0px;
    background-image: url("@/assets/logo.png");
}


#head-title-word{
    float: left;
    width: 200px;
    margin: 2px;
    
    line-height: 100px;
    font-weight: 700;
    font-size: 20px;
}

#head-currTime{
    float: right;
    margin: 1px 20px;
    padding: 0px;
    border: 0px;


    line-height: 100px;
    font-size: 15px;
    font-weight: 100;
    color: #606266;
}

#head-link-oceanData{
    float: right;
    margin: 0px 20px;
    padding: 0px;
    border: 0px;

    line-height: 100px;
}

#head-link-about{
    float: right;
    margin: 0px 10px;
    padding: 0px;
    border: 0px;

    line-height: 100px;
}
</style>