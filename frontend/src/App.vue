<template>
  <div id="app">

    <el-container>

      <el-aside id = "app-el-aside" style="width: 200px;">
        <AppSide/>
      </el-aside>


      <el-container>
        <el-header style="height: 100px;">
          <AppHead/>
        </el-header>

        <el-main>
          <!--AppMain-->
          <router-view></router-view>
        </el-main>
      </el-container>


    </el-container>
  </div>
</template>


<script>
import AppHead from '@/views/AppHead.vue'
import AppSide from '@/views/AppSide.vue'
export default{
  components:{
    AppHead:AppHead,
    AppSide:AppSide,
  }
}
</script>



<style>

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;

  display: flex;
  height: 100vh;
  flex-direction: column; /* 设置为列方向布局 */

  margin: 0;

  overflow: hidden;
}

.AppSide{
  margin: 0px;
  padding: 0px;
  border: 0px;
}

#app-el-aside{
  background-color: #00ff0010;
  height: 100vh;
  overflow-x: hidden;
  overflow-y: auto ;
}

.el-header{
  background-image: linear-gradient(to right, rgb(168,236,255), #FFFFFF);
  flex-shrink: 0;
}

.el-main{
  background-color: #f0f0f0;

  overflow: hidden;
}
</style>
