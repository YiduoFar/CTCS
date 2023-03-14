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
         * 登录
         * @param {*} state 
         * @param {*} user 
         * @returns 
         */
        async login(state, user) {
            return await axios({
                url: '/login',
                method: 'POST',
                data: qs.stringify(user)
            })
        },
        /**
         * 注销
         * @param {*} state 
         * @returns 
         */
        async logout(state) {
            return await axios({
                url: '/log_out',
                method: 'POST'
            })
        },
        /**
         * 注册
         * @param {*} state 
         * @param {*} user 
         * @returns 
         */
        async register(state, user) {
            return await axios({
                url: '/reg',
                method: 'POST',
                data: qs.stringify(user)
            })
        },
        async regVerifyUserName(state, userName) {
            return await axios({
                url: '/reg/verify/user_name',
                method: 'GET',
                params: {
                    userName: userName
                }
            })
        },
        async regVerifyPhone(state, phone) {
            return await axios({
                url: '/reg/verify/phone',
                method: 'GET',
                params: {
                    phone: phone
                }
            })
        },
        async regVerifyNumber(state, number) {
            return await axios({
                url: '/reg/verify/number',
                method: 'GET',
                params: {
                    number: number
                }
            })
        },
        async regVerifyEmail(state, email) {
            return await axios({
                url: '/reg/verify/email',
                method: 'GET',
                params: {
                    email: email
                }
            })
        },
        /**
         * 
         * @param {*} state 
         * @param {*} token 
         * @returns 
         */
        async tokenCheck(state) {
            return await axios({
                url: '/token/check',
                method: 'GET',
            })
        },
    },
    modules: {
    }
})