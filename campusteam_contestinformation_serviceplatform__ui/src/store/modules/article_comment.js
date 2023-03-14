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
         * @param {*} articleId
         * @returns 
         */
        async selectCommentVoByArticleId(state, articleId) {
            return await axios({
                url: '/article/comment/selectCommentVoByArticleId',
                method: 'GET',
                params: {
                    articleId: articleId
                }
            })
        },
        /**
         * 新增评论
         * @param {*} state 
         * @param {*} comment 
         */
        async insertComment(state, comment) {
            return await axios({
                url: '/article/comment/insertOne',
                method: 'POST',
                data: qs.stringify(comment)
            })
        },
        /**
         * 点赞评论
         * @param {*} state 
         * @param {*} comment 
         */
         async doCommentLikeById(state, commentId) {
            return await axios({
                url: '/article/comment/doLikeById',
                method: 'GET',
                params:{
                    commentId: commentId
                }
            })
        },
        /**
         * 点踩评论
         * @param {*} state 
         * @param {*} comment 
         */
         async doCommentTrampleById(state, commentId) {
            return await axios({
                url: '/article/comment/doTrampleById',
                method: 'GET',
                params:{
                    commentId: commentId
                }
            })
        },
    },
    modules: {
    }
})