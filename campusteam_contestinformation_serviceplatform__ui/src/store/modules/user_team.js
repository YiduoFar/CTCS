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
         * @param {*} userTeamDeleteVo 
         * @returns 
         */
        async deleteUserTeam(state, userTeamDeleteVo) {
            return await axios({
                url: '/user_team/deleteOne',
                method: 'POST',
                data: qs.stringify(userTeamDeleteVo)
            })
        },
        /**
         * 取申请者
         * @param {*} state 
         * @param {*} queryVo 
         * @returns 
         */
        async selectUserListByTeamIdAndStatus(state, queryVo) {
            return await axios({
                url: '/user_team/selectUserListByTeamIdAndStatus',
                method: 'GET',
                params: {
                    teamId: queryVo.teamId,
                    status: queryVo.status
                }
            })
        },
        /**
         * 同意申请
         * @param {*} state 
         * @param {*} userTeam 
         * @returns 
         */
        async updateUserTeamStatusById(state, userTeam) {
            return await axios({
                url: '/user_team/updateStatusById',
                method: 'POST',
                data: qs.stringify(userTeam)
            })
        },
        /**
         * 申请
         * @param {*} state 
         * @param {*} userTeam 
         * @returns 
         */
         async insertUserTeam(state, userTeam) {
            return await axios({
                url: '/user_team/insertOne',
                method: 'POST',
                data: qs.stringify(userTeam)
            })
        },
        /**
         * 申请前检查
         * @param {*} state 
         * @param {*} userTeam 
         * @returns 
         */
         async selectAndJudgeOne(state, judgeVo) {
            return await axios({
                url: '/user_team/selectAndJudgeOne',
                method: 'GET',
                params:{
                    userId: judgeVo.userId,
                    teamId: judgeVo.teamId
                }
            })
        },
        /**
         * 邀请码快速入队
         * @param {*} state 
         * @param {*} userTeam 
         * @returns 
         */
         async fastJoinTeam(state, formdata) {
            return await axios({
                url: '/user_team/fastJoinTeam',
                method: 'POST',
                data: formdata
            })
        },
    },
    modules: {
    }
})