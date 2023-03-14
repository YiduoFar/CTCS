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
         * 取参与者列表
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectTeamListByCompetitionIdAndStatus(state, queryVo) {
            return await axios({
                url: '/team_competition/selectTeamListByCompetitionIdAndStatus',
                method: 'GET',
                params: {
                    competitionId: queryVo.competitionId,
                    status: queryVo.status
                }
            })
        },
        /**
         * 剔除队伍 队伍退出竞赛
         * @param {*} state 
         * @param {*} teamCompetition 
         * @returns 
         */
        async deleteTeamCompetition(state, teamCompetition) {
            return await axios({
                url: '/team_competition/deleteOne',
                method: 'POST',
                data: qs.stringify(teamCompetition)
            })
        },
        /**
         * 同意申请
         * @param {*} state 
         * @param {*} userCompetition 
         * @returns 
         */
        async updateTeamCompetitionStatusById(state, teamCompetition) {
            return await axios({
                url: '/team_competition/updateStatusById',
                method: 'POST',
                data: qs.stringify(teamCompetition)
            })
        },
        /**
         * 申请 
         * @param {*} state 
         * @param {*} userCompetition 
         * @returns 
         */
         async insertTeamCompetition(state, teamCompetition) {
            return await axios({
                url: '/team_competition/insertOne',
                method: 'POST',
                data: qs.stringify(teamCompetition)
            })
        },
        /**
         * 检查是否已参加（队伍）
         * @param {*} state 
         * @param {*} vo 
         * @returns 
         */
        async selectTeamCompetitionByCaptionIdAndCompetitionId(state, vo) {
            return await axios({
                url: '/team_competition/selectByCaptionIdAndCompetitionId',
                method: 'GET',
                params:{
                    userId: vo.userId,
                    competitionId: vo.competitionId
                }
            })
        }
    },
    modules: {
    }
})