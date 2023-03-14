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
         * 查询所有竞赛 根据关键字
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectCompetitionByKeyWord(state, queryVo) {
            return await axios({
                url: '/show/competition/selectCompetitionByKeyWord',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 分页
         * 查询所有竞赛 根据状态（进行中、已结束）
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectCompetitionByStatus(state, queryVo) {
            return await axios({
                url: '/show/competition/selectCompetitionByStatus',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 分页
         * 查询我的竞赛 根据状态（进行中、已结束）、用户id、关键字
         * @param {*} state 
         * @param {*} queryVo
         * @returns 
         */
        async selectCompetitionByUserIdAndKeyWord(state, queryVo) {
            return await axios({
                url: '/competition/selectCompetitionByUserIdAndKeyWord',
                method: 'POST',
                data: qs.stringify(queryVo)
            })
        },
        /**
         * 上传文件 类型2
         * @param {*} state 
         * @param {*} formData 
         * @returns 
         */
        async uploadCompetitionFile(state, formData) {
            return await axios({
                url: '/competition/file/upload',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 上传文件 封面图片
         * @param {*} state 
         * @param {*} formData 
         * @returns 
         */
        async uploadCompetitionCover(state, formData) {
            return await axios({
                url: '/competition/file/uploadCover',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 查询文件url
         * @param {*} state 
         * @param {*} fileQueryVo 
         * @returns 
         */
        async selectFilePath(state, fileQueryVo) {
            return await axios({
                url: '/competition/file/selectFilePath',
                method: 'GET',
                params: {
                    competitionId: fileQueryVo.id,
                    competitionFileType: fileQueryVo.type
                }
            })
        },
        /**
         * 发布竞赛
         * @param {*} state 
         * @param {*} competition 
         * @returns 
         */
        async insertCompetition(state, competition) {
            return await axios({
                url: '/competition/insertCompetition',
                method: 'POST',
                data: qs.stringify(competition)
            })
        },
        /**
         * 根据发布者id查询竞赛
         * @param {*} state 
         * @param {*} queryVo 
         * @returns 
         */
        async selectCompetitionByUndertaker3Id(state, queryVo) {
            return await axios({
                url: '/competition/selectCompetitionByUndertaker3Id',
                method: 'GET',
                params: {
                    undertaker3Id: queryVo.userId,
                    keyword: queryVo.keyword
                }
            })
        },
        /**
         * 根据发布者id编辑竞赛
         * @param {*} state 
         * @param {*} competition 
         * @returns 
         */
        async updateCompetitionById(state, competition) {
            return await axios({
                url: '/competition/updateCompetitionById',
                method: 'POST',
                data: qs.stringify(competition)
            })
        },
        /**
         * 根据类型查询文件列表
         * @param {*} state 
         * @param {*} vo 
         * @returns 
         */
        async getCompetitionFileByCompetitionIdAndType(state, vo) {
            return await axios({
                url: '/competition/file/selectCompetitionFileByCompetitionIdAndType',
                method: 'GET',
                params: {
                    competitionId: vo.competitionId,
                    competitionFileType: vo.competitionFileType
                }
            })
        },
        /**
         * 根据类型查询文件列表
         * @param {*} state 
         * @param {*} file 
         * @returns 
         */
        async deleteCompetitionFileById(state, file) {
            return await axios({
                url: '/competition/file/deleteCompetitionFileById',
                method: 'POST',
                data: qs.stringify(file)
            })
        },
        /**
         * 根据id查询竞赛Vo
         * @param {*} state 
         * @param {*} competitionId 
         * @returns 
         */
        async selectCompetitionVoById(state, competitionId) {
            return await axios({
                url: 'competition/selectCompetitionVoById',
                method: 'GET',
                params: {
                    competitionId: competitionId
                }
            })
        },
        /**
         * 根据id查询竞赛
         * @param {*} state 
         * @param {*} competitionId 
         * @returns 
         */
        async selectCompetitionById(state, competitionId) {
            return await axios({
                url: '/competition/selectCompetitionById',
                method: 'GET',
                params: {
                    competitionId: competitionId
                }
            })
        },
        /**
         * 下架
         * @param {*} state 
         * @param {*} formData 
         * @returns 
         */
        async deleteCompetitionById(state, formData) {
            return await axios({
                url: '/competition/deleteCompetitionById',
                method: 'POST',
                data: formData
            })
        },
        /**
         * 查询轮播图Vo(封面)
         * @param {*} state 
         * @returns 
         */
        async selectSlideShowVo(state) {
            return await axios({
                url: '/show/competition/selectSlideShowVo',
                method: 'GET',
            })
        },
    },
    modules: {
    }
})