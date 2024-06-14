import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    prefixURL:'https://localhost:8432/api',
    weatherServiceURL :'https://api.open.geovisearth.com/weather/v1/fixed_point_forecast',
    weatherServiceToken:'',
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
