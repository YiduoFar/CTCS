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
         * 获取消息列表
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectMessageByUserId(state, userId) {
            return await axios({
                url: '/message/selectMessageByUserId',
                method: 'GET',
                params: {
                    userId: userId
                }
            })
        },
        /**
         * 清空消息列表
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
         async deleteMessageByUserId(state, formData) {
            return await axios({
                url: '/message/deleteMessageByUserId',
                method: 'POST',
                data: formData
            })
        },
    },
    modules: {
    }
})