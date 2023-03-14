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
         * 婉拒申请、剔除参与者
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async deleteUserActivity(state, userActivityDeleteVo) {
            return await axios({
                url: '/user_activity/deleteOne',
                method: 'POST',
                data: qs.stringify(userActivityDeleteVo)
            })
        },
        /**
         * 申请
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async insertUserActivity(state, userActivity) {
            return await axios({
                url: '/user_activity/insertOne',
                method: 'POST',
                data: qs.stringify(userActivity)
            })
        },
        /**
         * 查是否存在
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async selectUserActivity(state, userActivity) {
            return await axios({
                url: '/user_activity/selectOne',
                method: 'POST',
                data: qs.stringify(userActivity)
            })
        },
        /**
         * 取参与者列表
         * @param {*} state 
         * @param {*} userActivity
         * @returns 
         */
        async selectUserListByActivityIdAndStatus(state, queryVo) {
            return await axios({
                url: '/user_activity/selectUserListByActivityIdAndStatus',
                method: 'GET',
                params: {
                    activityId: queryVo.activityId,
                    status: queryVo.status
                }
            })
        },
        /**
         * 婉拒申请、剔除参赛者
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async deleteUserActivity(state, userActivityDeleteVo) {
            return await axios({
                url: '/user_activity/deleteOne',
                method: 'POST',
                data: qs.stringify(userActivityDeleteVo)
            })
        },
        /**
         * 同意申请
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async updateUserActivityStatusById(state, userActivity) {
            return await axios({
                url: '/user_activity/updateStatusById',
                method: 'POST',
                data: qs.stringify(userActivity)
            })
        },
    },
    modules: {
    }
})