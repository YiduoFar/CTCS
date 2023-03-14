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
         * 根据队长id查询队伍
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async selectTeamByCaptionId(state, userId) {
            return await axios({
                url: '/team/selectTeamByCaptionId',
                method: 'GET',
                params: {
                    teamCaptionId: userId
                }
            })
        },
        /**
         * 根据队长id查询队伍Vo
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async selectTeamMemVoByCaptionId(state, userId) {
            return await axios({
                url: '/team/selectTeamMemVoByCaptionId',
                method: 'GET',
                params: {
                    teamCaptionId: userId
                }
            })
        },
        /**
         * 新增队伍
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async insertTeam(state, team) {
            return await axios({
                url: '/team/insertTeam',
                method: 'POST',
                data: qs.stringify(team)
            })
        },
        /**
         * 根据id查询队伍
         * @param {*} state 
         * @param {*} teamId
         * @returns 
         */
        async selectTeamById(state, teamId) {
            return await axios({
                url: '/team/selectTeamById',
                method: 'GET',
                params: {
                    teamId: teamId
                }
            })
        },
        /**
         * 根据id编辑队伍
         * @param {*} state 
         * @param {*} teamId
         * @returns 
         */
        async updateTeamById(state, team) {
            return await axios({
                url: '/team/updateTeamById',
                method: 'POST',
                data: qs.stringify(team)
            })
        },
        /**
         * 根据id删除队伍
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async deleteTeamById(state, team) {
            return await axios({
                url: '/team/deleteTeamById',
                method: 'POST',
                data: qs.stringify(team)
            })
        },
        /**
         * 查询队伍Vo
         * @param {*} state 
         * @param {*} teamId
         * @returns 
         */
        async selectTeamDetailVoById(state, teamId) {
            return await axios({
                url: '/team/selectTeamDetailVoById',
                method: 'GET',
                params: {
                    teamId: teamId
                }
            })
        },
        /**
         * 查询招募队伍
         * @param {*} state 
         * @param {*} teamId
         * @returns 
         */
        async selectTeamRecruit(state) {
            return await axios({
                url: '/team/selectTeamRecruit',
                method: 'GET'
            })
        },
        /**
         * 查询最近加入队伍
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async selectTeamRencentlyJoinedByUserId(state, userId) {
            return await axios({
                url: '/team/selectTeamRencentlyJoinedByUserId',
                method: 'GET',
                params: {
                    userId: userId
                }
            })
        },
        /**
         * 关键字查询队伍
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectTeamByKeyword(state, queryVo) {
            return await axios({
                url: '/team/selectTeamByKeyword',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 队伍星级排行
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async selectTeamStar(state) {
            return await axios({
                url: '/team/selectTeamStar',
                method: 'GET'
            })
        },
    },
    modules: {
    }
})