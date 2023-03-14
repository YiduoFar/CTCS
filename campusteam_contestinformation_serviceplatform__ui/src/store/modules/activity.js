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
         * 分页
         * 查询所有活动 根据关键字
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectActivityByKeyword(state, queryVo) {
            return await axios({
                url: '/show/activity/selectActivityByKeyword',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 查询最新的5个活动
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectLatestActivity(state) {
            return await axios({
                url: '/show/activity/selectLatestActivity',
                method: 'GET'
            })
        },
        /**
         * 根据id查询ActivityDetailVo
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectActivityDetailVoById(state, formData) {
            return await axios({
                url: '/activity/selectActivityDetailVoById',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 分页
         * 查询我发布的活动 根据状态（进行中、已结束）、用户id、关键字
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectActivityByInitiatorId(state, queryVo) {
            return await axios({
                url: '/activity/selectActivityByInitiatorId',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 分页
         * 查询我参加的活动 根据状态（进行中、已结束）、用户id、关键字
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectMyActivityByKeyWordAndStatus(state, queryVo) {
            return await axios({
                url: '/activity/selectMyActivityByKeyWordAndStatus',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 上传活动资料
         * @param {*} state 
         * @param {*} formData
         * @returns 
         */
        async uploadActivityFile(state, formData) {
            return await axios({
                url: '/activity/file/upload',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 上传活动封面
         * @param {*} state 
         * @param {*} formData
         * @returns 
         */
        async uploadActivityCover(state, formData) {
            return await axios({
                url: '/activity/file/uploadCover',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 根据id删除活动
         * @param {*} state 
         * @param {*} formData
         * @returns 
         */
        async deleteActivityById(state, formData) {
            return await axios({
                url: '/activity/deleteActivityById',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 发布活动
         * @param {*} state 
         * @param {*} act 
         * @returns 
         */
        async insertActivity(state, act) {
            return await axios({
                url: '/activity/insertActivity',
                method: 'POST',
                data: qs.stringify(act)
            })
        },
        /**
         * 根据id查询活动
         * @param {*} state 
         * @param {*} activityId 
         * @returns 
         */
        async selectActivityById(state, activityId) {
            return await axios({
                url: '/activity/selectActivityById',
                method: 'GET',
                params: {
                    activityId: activityId
                }
            })
        },
        /**
         * 根据发布者id编辑活动
         * @param {*} state 
         * @param {*} activity 
         * @returns 
         */
        async updateActivityById(state, act) {
            return await axios({
                url: '/activity/updateActivityById',
                method: 'POST',
                data: qs.stringify(act)
            })
        },
    },
    modules: {
    }
})