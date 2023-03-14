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
         * 退出竞赛
         * @param {*} state 
         * @param {*} userCompetition
         * @returns 
         */
        async quitCompetition(state, userCompetition) {
            return await axios({
                url: '/user_competition/quitCompetition',
                method: 'POST',
                data: qs.stringify(userCompetition)
            })
        },
        /**
         * 取参与者列表
         * @param {*} state 
         * @param {*} userCompetition
         * @returns 
         */
         async selectUserListByCompetitionIdAndStatus(state, queryVo) {
            return await axios({
                url: '/user_competition/selectUserListByCompetitionIdAndStatus',
                method: 'GET',
                params: {
                    competitionId: queryVo.competitionId,
                    status: queryVo.status
                }
            }) 
        },
        /**
         * 婉拒申请、剔除参赛者
         * @param {*} state 
         * @param {*} userCompetition 
         * @returns 
         */
        async deleteUserCompetition(state, userCompetitionDeleteVo) {
            return await axios({
                url: '/user_competition/deleteOne',
                method: 'POST',
                data: qs.stringify(userCompetitionDeleteVo)
            })
        },
        /**
         * 同意申请
         * @param {*} state 
         * @param {*} userCompetition 
         * @returns 
         */
        async updateUserCompetitionStatusById(state, userCompetition) {
            return await axios({
                url: '/user_competition/updateStatusById',
                method: 'POST',
                data: qs.stringify(userCompetition)
            })
        },
        /**
         * 申请
         * @param {*} state 
         * @param {*} userCompetition 
         * @returns 
         */
        async insertUserCompetition(state, userCompetition) {
            return await axios({
                url: '/user_competition/insertOne',
                method: 'POST',
                data: qs.stringify(userCompetition)
            })
        },
        
        /**
         * 查是否存在
         * @param {*} state 
         * @param {*} userCompetition 
         * @returns 
         */
         async selectUserCompetition(state, userCompetition) {
            return await axios({
                url: '/user_competition/selectOne',
                method: 'POST',
                data: qs.stringify(userCompetition)
            })
        },
    },
    modules: {
    }
})