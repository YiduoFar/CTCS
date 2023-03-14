import Vue from 'vue'
import Vuex from 'vuex'
import login from './modules/login'
import user from './modules/user'
import competition from './modules/competition'
import user_competition from './modules/user_competition'
import team_competition from './modules/team_competition'
import activity from './modules/activity'
import user_activity from './modules/user_activity'
import team_activity from './modules/team_activity'
import message from './modules/message'
import team from './modules/team'
import count from './modules/count'
import user_team from './modules/user_team'
import article from './modules/article'
import article_comment from './modules/article_comment'

import axios from 'axios'
import qs from 'qs'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    /**
     * 全平台管理
     * @param {*} systemDomainVo 
     * @returns 
     */
    async systemMange(state, systemDomainVo) {
      return await axios({
        url: '/system/domain/manage',
        method: 'POST',
        data: qs.stringify(systemDomainVo)
      })
    },
    /**
     * 获取新闻
     * @param {*} state 
     * @returns 
     */
    async selectNews(state) {
      return await axios({
        url: '/news/selectAll',
        method: 'GET'
      })
    },
    /**
     * 分页
     * 获取新闻
     * @param {*} state 
     * @returns 
     */
    async selectNewsPage(state, queryVo) {
      return await axios({
        url: '/show/news/selectPage',
        method: 'POST',
        data: qs.stringify(queryVo)
      })
    },
    /**
     * 根据id获取新闻
     * @param {*} state 
     * @param {*} newsId 
     */
    async selectNewsById(state, newsId) {
      return await axios({
        url: '/news/selectById',
        method: 'GET',
        params: {
          newsId: newsId
        }
      })
    },
    /**
     * 上传新闻图片
     * @param {*} state 
     * @param {*} formData 
     */
    async newsImageUpload(state, formData) {
      return await axios({
        url: '/news/imageUpload',
        method: 'POST',
        data: formData
      })
    },
    /**
     * 删除新闻图片
     * @param {*} state 
     * @param {*} formData 
     */
    async newsImageDelete(state, imageUrl) {
      return await axios({
        url: '/news/imageDelete',
        method: 'POST',
        data: qs.stringify({
          imageUrl: imageUrl
        })
      })
    },
    /**
     * 发布新闻
     * @param {*} state 
     * @param {*} news 
     * @returns 
     */
    async insertNews(state, news) {
      return await axios({
        url: '/news/insertOne',
        method: 'POST',
        data: qs.stringify(news)
      })
    },
    /**
     * 删除新闻
     * @param {*} state 
     * @param {*} news 
     * @returns 
     */
    async deleteNews(state, news) {
      return await axios({
        url: '/news/deleteOne',
        method: 'POST',
        data: qs.stringify(news)
      })
    },
    /**
     * 获取公告
     * @param {*} state 
     * @returns 
     */
    async selectAnnouncement(state) {
      return await axios({
        url: '/announcement/selectAll',
        method: 'GET'
      })
    },
    /**
     * 分页
     * 获取公告
     * @param {*} state 
     * @returns 
     */
    async selectAnnouncementPage(state, queryVo) {
      return await axios({
        url: '/show/announcement/selectPage',
        method: 'POST',
        data: qs.stringify(queryVo)
      })
    },
    /**
     * 根据id获取公告
     * @param {*} state 
     * @param {*} newsId 
     */
    async selectAnnouncementById(state, announcementId) {
      return await axios({
        url: '/announcement/selectById',
        method: 'GET',
        params: {
          announcementId: announcementId
        }
      })
    },
    /**
     * 发布公告
     * @param {*} state 
     * @param {*} news 
     * @returns 
     */
    async insertAnnouncement(state, announcement) {
      return await axios({
        url: '/announcement/insertOne',
        method: 'POST',
        data: qs.stringify(announcement)
      })
    },
    /**
     * 删除公告
     * @param {*} state 
     * @param {*} announcement 
     * @returns
     */
    async deleteAnnouncement(state, announcement) {
      return await axios({
        url: '/announcement/deleteOne',
        method: 'POST',
        data: qs.stringify(announcement)
      })
    }
  },
  modules: {
    login, user, competition, user_competition, team_competition, activity, user_activity, team_activity, message, team, count, user_team, article, article_comment
  }
})
