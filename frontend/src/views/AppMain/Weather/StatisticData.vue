<template>
    <div id="StatisticData" style="background-color: #f0f0f0">
        <div ref="myChart" id="my-chart"></div>
    </div>
</template>

<script>
import * as echarts from 'echarts'

export default {

    name: "StatisticData",

    data() {
        return {

            chartOptions: {
                // 全局调色盘。
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { type: 'cross' }
                },
                xAxis: {
                    type: 'category',
                    data: ['A', 'B', 'C']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [120, 200, 150],
                        type: 'line',
                        smooth: true,
                    }
                ]
            }
        };
    },
    methods: {
        echartSetup(ncRangeList, sstValueList) {

            this.chartOptions.xAxis.data = ncRangeList
            this.chartOptions.series[0].data = sstValueList

            this.myChart = echarts.init(this.$refs.myChart)
            this.myChart.setOption(this.chartOptions)
            console.log(this.myChart)

        },
        echartDestroy() {
            this.myChart.dispose()
        }
    }
}

</script>

<style scoped>
#my-chart {

    width: 80%;
    height: 600px;
    margin: 0 auto;
}
</style>