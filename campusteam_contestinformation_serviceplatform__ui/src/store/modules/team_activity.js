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
         * 剔除队伍 队伍退出活动
         * @param {*} state 
         * @param {*} teamActivity 
         * @returns 
         */
        async deleteTeamActivity(state, teamActivityDeleteVo) {
            return await axios({
                url: '/team_activity/deleteOne',
                method: 'POST',
                data: qs.stringify(teamActivityDeleteVo)
            })
        },
        /**
         * 申请 
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async insertTeamActivity(state, teamActivity) {
            return await axios({
                url: '/team_activity/insertOne',
                method: 'POST',
                data: qs.stringify(teamActivity)
            })
        },
        /**
         * 检查是否已参加（队伍）
         * @param {*} state 
         * @param {*} vo 
         * @returns 
         */
        async selectTeamActivityByCaptionIdAndActivityId(state, vo) {
            return await axios({
                url: '/team_activity/selectByCaptionIdAndActivityId',
                method: 'GET',
                params: {
                    userId: vo.userId,
                    activityId: vo.activityId
                }
            })
        },
        /**
         * 取参与者列表
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectTeamListByActivityIdAndStatus(state, queryVo) {
            return await axios({
                url: '/team_activity/selectTeamListByActivityIdAndStatus',
                method: 'GET',
                params: {
                    activityId: queryVo.activityId,
                    status: queryVo.status
                }
            })
        },
        /**
         * 剔除队伍 队伍退出活动
         * @param {*} state 
         * @param {*} teamActivity 
         * @returns 
         */
        async deleteTeamActivity(state, teamActivity) {
            return await axios({
                url: '/team_activity/deleteOne',
                method: 'POST',
                data: qs.stringify(teamActivity)
            })
        },
        /**
         * 同意申请
         * @param {*} state 
         * @param {*} userActivity 
         * @returns 
         */
        async updateTeamActivityStatusById(state, teamActivity) {
            return await axios({
                url: '/team_activity/updateStatusById',
                method: 'POST',
                data: qs.stringify(teamActivity)
            })
        },
    },
    modules: {
    }
})