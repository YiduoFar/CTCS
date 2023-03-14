import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import qs from 'qs'

Vue.use(Vuex)

// 导出方式区分index.js
export default ({
    // 声明命名空间
    namespaced: true,
    state: {
    },
    mutations: {
    },
    actions: {
        /**
         * 得到积分排行榜
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async getUserPointVoList(state) {
            return await axios({
                url: '/show/getUserPointVoList',
                method: 'GET'
            })
        },
        /**
         * 得到优秀个人
         * @param {*} state 
         * @param {*} queryVo
         * @returns
         */
        async getExcellentPlayerVoList(state) {
            return await axios({
                url: '/show/getExcellentPlayerVoList',
                method: 'GET'
            })
        },
        /**
         * 优秀队伍
         * @param {*} state 
         * @returns 
         */
        async getExcellentTeam(state) {
            return await axios({
                url: '/show/team/getExcellentTeam',
                method: 'GET'
            })
        }
    },
    modules: {
    }
})