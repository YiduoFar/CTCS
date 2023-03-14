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
         * 查询用户信息
         * @param {*} state 
         * @param {*} phone 
         * @returns 
         */
        async selectOneByPhone(state, phone) {
            return await axios({
                url: '/user/selectOneByPhone',
                method: 'GET',
                params: {
                    phone: phone
                }
            })
        },
        /**
         * 编辑用户信息
         * @param {*} state 
         * @param {*} phone 
         * @returns 
         */
        async updateOneById(state, user2) {
            return await axios({
                url: '/user/updateOneById',
                method: 'POST',
                data: qs.stringify(user2)
            })
        },
        /**
         * 查名字
         * @param {*} state 
         * @param {*} phone 
         * @returns 
         */
        async selectNameById(state, id) {
            return await axios({
                url: '/user/selectUserNameById',
                method: 'GET',
                params: {
                    id: id
                }
            })
        },
        /**
         * 上传头像
         * @param {*} state 
         * @param {*} phone 
         * @returns 
         */
        async uploadHeadImg(state, formData) {
            return await axios({
                url: '/user/uploadHeadImgById',
                method: 'POST',
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                data: formData
            })
        },
        /**
         * 查询用户详情Vo
         * @param {*} state 
         * @param {*} userId 
         * @returns 
         */
        async selectUserDetailVoById(state, userId) {
            return await axios({
                url: '/user/selectUserDetailVoById',
                method: 'GET',
                params: {
                    userId: userId
                }
            })
        },
        /**
         * 解封
         * @param {*} state 
         * @param {*} systemDomainVo 
         * @returns 
         */
        async unseal(state, systemDomainVo) {
            return await axios({
                url: '/user/unseal',
                method: 'POST',
                data: qs.stringify(systemDomainVo)
            })
        }

    },
    modules: {
    }
})