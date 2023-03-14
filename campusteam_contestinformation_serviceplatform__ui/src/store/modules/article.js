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
         * 根据作者id查询帖子
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async selectArticleByAuthorId(state, userId) {
            return await axios({
                url: '/article/selectArticleByAuthorId',
                method: 'GET',
                params: {
                    authorId: userId
                }
            })
        },
        /**
         * 发布帖子
         * @param {*} state 
         * @param {*} article 
         * @returns 
         */
        async insertArticle(state, article) {
            return await axios({
                url: '/article/insertArticle',
                method: 'POST',
                data: qs.stringify(article)
            })
        },
        /**
         * 根据帖子id查询帖子
         * @param {*} state 
         * @param {*} userId
         * @returns 
         */
        async selectArticleById(state, articleId) {
            return await axios({
                url: '/article/selectArticleById',
                method: 'GET',
                params: {
                    articleId: articleId
                }
            })
        },
        /**
         * 访问量+1
         * @param {*} state 
         * @param {*} articleId
         * @returns 
         */
        async doAddPageView(state, articleId) {
            return await axios({
                url: '/article/doAddPageView',
                method: 'GET',
                params: {
                    articleId: articleId
                }
            })
        },
        /**
         * 点赞
         * @param {*} state 
         * @param {*} articleId
         * @returns 
         */
         async doLikeById(state, articleId) {
            return await axios({
                url: '/article/doLikeById',
                method: 'GET',
                params: {
                    articleId: articleId
                }
            })
        },
        /**
         * 点踩
         * @param {*} state 
         * @param {*} articleId
         * @returns 
         */
         async doTrampleById(state, articleId) {
            return await axios({
                url: '/article/doTrampleById',
                method: 'GET',
                params: {
                    articleId: articleId
                }
            })
        },
        /**
         * 删除帖子
         * @param {*} state 
         * @param {*} article 
         * @returns 
         */
         async deleteArticle(state, article) {
            return await axios({
                url: '/article/deleteOne',
                method: 'POST',
                data: qs.stringify(article)
            })
        },
        /**
         * 分页
         * 根据关键字查询帖子列表
         * @param {*} queryVo 
         */
        async selectArticleByKeyword(state, queryVo) {
            return await axios({
                url: '/show/article/selectArticleByKeyword',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 上传帖子图片
         * @param {*} state 
         * @param {*} formData 
         * @returns
         */
        async articleImageUpload(state, formData) {
            return await axios({
                url: '/article/articleImageUpload',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 删除帖子图片
         * @param {*} state 
         * @param {*} formData 
         * @returns
         */
         async articleImageDelete(state, imageUrl) {
            return await axios({
                url: '/article/articleImageDelete',
                method: 'POST',
                data: qs.stringify({
                    imageUrl: imageUrl
                })
            })
        }
    },
    modules: {
    }
})