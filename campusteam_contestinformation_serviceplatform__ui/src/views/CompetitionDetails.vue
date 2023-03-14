<template>
    <div class="competition-details">
        <Nav></Nav>
        <div class="competition-details-body">
            <!-- 面包屑 -->
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/competition_hall' }">竞赛</el-breadcrumb-item>
                <el-breadcrumb-item>竞赛详情</el-breadcrumb-item>
            </el-breadcrumb>
            <!-- intro -->
            <div class="competition-details-intro ">
                <!-- 封面 -->
                <img :src="competition.competitionCover" alt="" class="competition-cover">
                <!-- 承办方 -->
                <div class="competition-undertaker">{{ competition.competitionUndertaker }}</div>
                <!-- 全称 -->
                <div class="competition-name">{{ competition.competitionName }}</div>
                <!-- 比赛时间 -->
                <div class="competition-time">比赛进行时间：{{ competition.competitionBeginTime }} ~ {{
                    competition.competitionEndTime
                }}</div>
                <!-- 比赛时间 -->
                <div class="competition-teamUpNum-limit" v-if="competition.isTeamUp == 1">
                    队伍人数限制：{{ competition.signUpMinTeamMemberNum }}~{{ competition.signUpMaxTeamMemberNum }}人
                </div>
                <!-- 比赛类型 -->
                <div class="competiton-type">{{ competition.competitionType == 0 ? '工科竞赛' : '文科竞赛' }}</div>
                <!-- 受众 -->
                <div class="competition-audience">{{
                    competition.competitionAudience == 0 ? '面向全校' :
                    competition.competitionAudience == 1 ? '面向学院' :
                        competition.competitionAudience == 2 ? '面向专业' : '面向个人'
                }}</div>
                <!-- 具体受众 -->
                <div class="competition-audience-name" v-if="competition.competitionAudience != 0">
                    {{ competition.competitionAudienceName }}</div>
                <!-- 参与方式 -->
                <div class="competition-isTeamUp">{{ competition.isTeamUp == 0 ? '个人参赛' : '组队参赛' }}</div>
                <!-- 已经报名数量 -->
                <div class="competition-joined-num">已有 {{ competition.competitionJoinedNum }} {{
                    competition.isTeamUp ==
                    0 ? '人' : '支队伍'
                }}预报名</div>
                <!-- 立即预报名btn -->
                <div class="join-btn" v-if="competition.competitionJoinedIsOpen == 1" @click="joinCompetition()">
                    立即预报名
                </div>
                <div class="join-btn" v-if="competition.competitionJoinedIsOpen == 0">
                    暂未开放
                </div>
                <!-- 负责人 -->
                <el-popover placement="top-start" title="" width="200" trigger="hover"
                    :content="String(competition.competitionUndertaker3.realName).substring(0, 1) + '老师联系方式: ' + competition.competitionUndertaker3.phone">
                    <div slot="reference" class="competition-undertaker3">{{ competition.competitionUndertaker3.realName }}
                    </div>
                </el-popover>
                <!-- 校内承办单位 -->
                <div class="competition-undertaker2">{{ competition.competitionUndertaker2 }}</div>
                <!-- 查看指导老师 -->
                <div class="competition-advisor-list-btn" @click="advisorListVisible = true">
                    查看指导老师信息
                </div>
            </div>
            <!-- 步骤 -->
            <div class="competition-details-stage ">
                <el-steps :active="activeStage" align-center>
                    <el-step :title="item.competitionStageName" v-for="item in competition.competitionStage">
                        <template slot="description">
                            <span>{{ item.competitionStageBeginTime }}{{ item.competitionStageEndTime == '' ? '' : '~' }}{{
                                item.competitionStageEndTime }}<br>{{ item.competitionStageIntro }}</span>
                        </template>
                    </el-step>
                </el-steps>
            </div>
            <!-- 信息标签页 -->
            <div class="competition-details-tags ">
                <el-tabs v-model="activeName">
                    <!-- 竞赛信息 -->
                    <el-tab-pane label="竞赛信息" name="first">
                        <!-- 1234..v-for打印信息 -->
                        <div class="competition-information" v-for="item, index in competition.competitionInformationList">
                            <div class="info-title">{{ chineseNum[index] }}、{{ item.infoTitle }}</div>
                            <div class="info-content">
                                {{ item.infoContent }}
                            </div>
                        </div>
                        <!-- 附件 -->
                        <div class="competition-attachments">
                            <div class="info-title" v-if="competition.competitionAttachmentList.length != 0">附件</div>
                            <div class="attachments-content" v-for="item, index in competition.competitionAttachmentList"
                                @click="downloadAttachments(item.competitionFileUrl)">
                                <span style="color: #409EFF;cursor: pointer;">{{ index + 1 }}. {{
                                    item.competitionFileName
                                }}</span>
                            </div>
                        </div>
                        <Foot class="tags1-foot"></Foot>
                    </el-tab-pane>
                    <!-- 预报名名单 -->
                    <el-tab-pane label="已预报名" name="second">
                        <div class="competition-joined-view ">
                            <!-- 人 -->
                            <div class="competition-joined ts01s" v-for="item in competition.joinedUserList"
                                v-show="competition.isTeamUp == 0">
                                <img :src="item.userHead" alt="" class="joined-head">
                                <div class="joined-name "><span>{{ item.userName }}</span></div>
                            </div>
                            <!-- 队 -->
                            <div class="competition-joined ts01s" v-for="item in competition.joinedTeamList"
                                v-show="competition.isTeamUp == 1">
                                <img :src="item.teamLogo" alt="" class="joined-head">
                                <div class="joined-name "><span>{{ item.teamName }}</span></div>
                            </div>
                        </div>
                        <Foot class="tags1-foot"></Foot>
                    </el-tab-pane>
                    <!-- 成绩下载 -->
                    <el-tab-pane label="成绩" name="third" v-if="competition.competitionStatus == 0">
                        <el-empty description="暂无成绩" :image-size="150"
                            v-if="competition.competitionResultList.length == 0"></el-empty>
                        <div class="competition-result-view">
                            <div class="competition-result-item" v-for="item, index in competition.competitionResultList">
                                <div class="result-file-name ts02s ">{{ index + 1 }}. {{ item.competitionFileName }}</div>
                                <!-- <div class="result-file-url">{{ item.resultFileDownloadURL }}</div> -->
                                <div class="result-result-announce-time">{{ item.competitionFileAnnounceTime.substring(0,
                                    10) }}
                                </div>
                                <div class="result-download-btn" @click="downloadAttachments(item.competitionFileUrl)">下载
                                </div>
                            </div>
                        </div>
                        <Foot class="tags1-foot"></Foot>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <!-- <Foot class="competition-details-foot"></Foot> -->
        </div>
        <!-- 指导老师名单弹出框 -->
        <el-dialog title="指导老师名单" :visible.sync="advisorListVisible" width="30%" center>
            <span>{{ competition.competitionAdvisorList }}</span>
            <span slot="footer" class="dialog-footer">
            </span>
        </el-dialog>
        <!-- 预报名队伍弹出框 -->
        <el-dialog title="选择队伍" :visible.sync="visible1" width="30%" right>
            <el-empty description="您尚未创建任何队伍" :image-size="90" v-if="teamList == null || teamList.length == 0"></el-empty>
            <div class="my-team-list ">
                <div class="my-team-item ts02s" v-for="item, index in teamList" @click="doChooseTeam(item, index)">
                    <img :src="item.teamLogo" alt="" class="team-logo">
                    <img :src="item.teamLogo" alt="" class="team-logo2">
                    <div class="team-name ">{{ item.teamName }}</div>
                    <div class="team-mem-num ">{{ item.teamMemberNum }} / {{ item.teamMemberNumMax }}</div>
                    <div class="team-label ">
                        <span class="team-label-item" v-for="e in item.teamLabel">{{ e }}</span>
                    </div>
                </div>
            </div>
            <span slot="footer" class="dialog-footer" v-if="teamList != null && teamList.length != 0">
                <el-button size="middle" round type="primary"
                    @click="joinCompetitionForTeam(); visible1 = false">确定</el-button>
                <el-button size="middle" round @click="visible1 = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import ElementUI from 'element-ui'
import { mapActions } from 'vuex'
export default {
    data: () => ({
        /**
         * 默认选中tab标签页
         */
        activeName: 'first',
        /**
         * 指导老师el-dialog控制变量
         */
        advisorListVisible: false,
        /**
         * 内容标题大写数字前缀
         */
        chineseNum: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十'],
        /**
         * 作为队伍参赛时，队伍选择的el-dialog控制变量
         */
        visible1: false,
        /**
         * 以当前登录用户作为队长的队伍列表
         */
        teamList: [],
        /**
         * 用户选择进行参赛的队伍实体
         */
        chooseTeam: {},
        /**
         * 竞赛Vo实体
         */
        competition: {
            competitionId: '1', // 竞赛id
            competitionName: '第十四届蓝桥杯大赛广东省选拔赛', // 竞赛名称
            competitionUndertaker: '工信部人才发展中心', // 承办方
            competitionJoinedNum: '13', // 已报名数量
            isTeamUp: '0', // 是否需要组队报名 0否 1是
            signUpMinTeamMemberNum: '5', // 若需要组队报名，则队伍最低人数
            signUpMaxTeamMemberNum: '8', // 若需要组队报名，则队伍最多人数
            competitionAudience: '1', // 受众类型 0面向全校、1面向学院、2面向专业、3面向个人
            competitionType: '0', // 竞赛类型 0工科竞赛、1文科竞赛
            competitionStageNow: '4', // 当前步骤
            competitionStage: [], // 竞赛实时状态
            competitionUndertaker2: '信息与通信工程学院', // 校内承办单位
            competitionUndertaker3: {},// 负责人
            competitionAdvisorList: '', // 指导老师信息
            competitionBeginTime: '2023-02-13 18:30:00', // 开始时间 
            competitionEndTime: '2023-02-13 21:00:00', // 结束时间 
            competitionInformationList: [], // 内容
            competitionExtraPoints: '', // 预计综测加分
            competitionAttachmentList: [], // 附件
            competitionResultList: [], // 竞赛结果
            joinedUserList: [], // 加入者列表
            joinedTeamList: [] // 加入队伍列表
        },
    }),
    created() {
        this.init()
    },
    methods: {
        /**
         * 通过队长id查询队伍
         */
        ...mapActions('team', ['selectTeamByCaptionId']),
        /**
         * 通过竞赛id查询竞赛Vo
         */
        ...mapActions('competition', ['selectCompetitionVoById']),
        /**
         * 新增用户竞赛关系UserCompetition
         */
        ...mapActions('user_competition', ['insertUserCompetition']),
        /**
         * 查询当前用户的所有用户竞赛关系
         */
        ...mapActions('user_competition', ['selectUserCompetition']),
        /**
         * 新增队伍竞赛关系TeamCompetition
         */
        ...mapActions('team_competition', ['insertTeamCompetition']),
        /**
         * 根据队长id和竞赛id查询队伍竞赛关系TeamCompetition
         */
        ...mapActions('team_competition', ['selectTeamCompetitionByCaptionIdAndCompetitionId']),
        /**
         * 初始化
         */
        async init() {
            // 获取竞赛实体
            const { code, message, data } = await this.selectCompetitionVoById(this.$route.query.competitionId)
            if (code === 200) {
                this.competition = { ...data }
                // 处理时间
                this.competition.competitionBeginTime = this.$moment(this.competition.competitionBeginTime).format('YYYY-MM-DD')
                this.competition.competitionEndTime = this.$moment(this.competition.competitionEndTime).format('YYYY-MM-DD')
                // 处理步骤
                this.competition.competitionStage = [...JSON.parse(this.competition.competitionStage)]
                // 循环
                this.competition.competitionStage.forEach((item) => {
                    // 如果同一天
                    if (this.$moment(item.competitionStageBeginTime).format('YYYY-MM-DD') == this.$moment(item.competitionStageEndTime).format('YYYY-MM-DD')) {
                        // format时间为MM.DD H点
                        item.competitionStageBeginTime = this.$moment(item.competitionStageBeginTime).format('MM.DD H点')
                        if (item.competitionStageEndTime) {
                            item.competitionStageEndTime = this.$moment(item.competitionStageEndTime).format('H点')
                        }
                    } else {
                        // 如果不同一天
                        // format时间为MM-DD
                        item.competitionStageBeginTime = this.$moment(item.competitionStageBeginTime).format('MM-DD')
                        // 如果无结束时间则不format
                        if (item.competitionStageEndTime) {
                            item.competitionStageEndTime = this.$moment(item.competitionStageEndTime).format('MM-DD')
                        }
                    }
                })
                // 反序列化竞赛内容
                this.competition.competitionInformationList = [...JSON.parse(this.competition.competitionInformationList)]
                // 如果竞赛是组队报名，则获取以当前登录用户为首的队伍以供选择
                if (this.competition.isTeamUp == 1) {
                    let userId = localStorage.getItem('userId')
                    const { code, message, data } = await this.selectTeamByCaptionId(userId)
                    if (code === 200 && data != null) {
                        this.teamList = [...data]
                        // TODO: 修改
                        this.teamList.forEach((item) => {
                            item.teamLabel = JSON.parse(item.teamLabel)
                        })
                    }
                }
            }
        },
        /**
         * 参加竞赛
         */
        async joinCompetition() {
            // 个人参加
            if (this.competition.isTeamUp == 0) {
                // 构造userCompetition
                let userCompetition = {
                    userId: localStorage.getItem('userId'),
                    competitionId: this.competition.competitionId,
                    status: 0
                }
                // 检查是否已经参加
                const { code, message, data } = await this.selectUserCompetition(userCompetition)
                if (code === 200) {
                    // data不为null，则已经提交申请或参加竞赛
                    if (data != null) {
                        ElementUI.Message.warning(data.status == 0 ? '您已提交报名申请' : '您已参赛')
                        return
                    }
                }
                let _this = this
                this.$confirm(_this.competition.competitionName, '确定参加竞赛?', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(async () => {
                    // 参加竞赛，新增记录
                    const { code, message, data } = await _this.insertUserCompetition(userCompetition)
                    if (code === 200) {
                        ElementUI.Message.success('申请成功')
                    }
                })
            }
            // 队伍参加
            if (this.competition.isTeamUp == 1) {
                // 构造检查Vo
                let vo = {
                    userId: localStorage.getItem('userId'),
                    competitionId: this.competition.competitionId
                }
                // 检查自己是否已经有队伍参加了这个竞赛
                const { code, message, data } = await this.selectTeamCompetitionByCaptionIdAndCompetitionId(vo)
                if (code === 200) {
                    if (data != null) {
                        ElementUI.Message.warning(data.status == 0 ? '您的一支队伍已提交了报名申请' : '您已有一支参赛队伍，请勿重复报名')
                        return
                    }
                }
                // 打开选择参赛队伍的el-dialog
                this.visible1 = true
            }
        },
        /**
         * 选择队伍样式变换
         * @param {*} item 
         * @param {*} index 
         */
        doChooseTeam(item, index) {
            this.chooseTeam = { ...item }
            let teams = document.getElementsByClassName('my-team-item')
            for (let i = 0; i < teams.length; i++) {
                if (i == index) {
                    teams[i].classList.add('my-team-item-active')
                    teams[i].classList.remove('my-team-item-normal')
                } else {
                    teams[i].classList.add('my-team-item-normal')
                    teams[i].classList.remove('my-team-item-active')
                }
            }
        },
        /**
         * 队伍申请参加竞赛
         */
        async joinCompetitionForTeam() {
            let teamCompetition = {
                teamId: this.chooseTeam.teamId,
                competitionId: this.competition.competitionId,
                status: 0
            }
            const { code, message, data } = await this.insertTeamCompetition(teamCompetition)
            if (code === 200) {
                ElementUI.Message.success('申请成功')
            }
        },
        /**
         * 单个文件下载 （新开窗口）
         * @param {*} url 
         */
        downloadAttachments(url) {
            window.open(url, '_blank')
        },
    },
    computed: {
        /**
         * 当前步骤
         */
        activeStage() {
            return parseInt(this.competition.competitionStageNow) + 1
        }
    },
    components: {
        Nav, Foot
    },
}
</script>

<style lang="less" scoped>
.competition-details {
    width: 100%;
    height: auto;

    .competition-details-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;
        background-color: #fafafa;

        .competition-details-intro {
            position: absolute;
            top: 125px;
            width: 66%;
            left: 17%;
            height: 200px;
            overflow: hidden;
            background-image: linear-gradient(120deg, #e0c3fc 0%, #8ec5fc 100%);

            .competition-cover {
                position: relative;
                // top: -150px;
                width: 100%;
                height: 100%;
                z-index: 0;
                // 背景模糊100px
                filter: blur(100px) brightness(80%);
            }

            .competition-undertaker {
                position: absolute;
                font-size: 13px;
                top: 13px;
                left: 25px;
                color: #ffffff;
                font-weight: bold;
                letter-spacing: 2px;
                text-shadow: 0px 0px 80px #000;
            }

            .competition-name {
                position: absolute;
                font-size: 30px;
                top: 35px;
                left: 25px;
                color: #ffffff;
                font-weight: bold;
                letter-spacing: 2px;
                text-shadow: 0px 0px 100px #000;
            }

            .competition-time {
                position: absolute;
                font-size: 13px;
                top: 85px;
                left: 25px;
                color: #fff;
            }

            .competition-teamUpNum-limit {
                position: absolute;
                font-size: 13px;
                top: 108px;
                left: 25px;
                color: #fff;
            }

            .competiton-type {
                position: absolute;
                font-size: 12px;
                right: 20px;
                top: 15px;
                color: #ffffff;
                padding-left: 8px;
                padding-right: 8px;
                height: 25px;
                line-height: 25px;
                border-radius: 5px;
                background-color: #10ee99;
                font-weight: bold;

            }

            .competition-audience {
                position: absolute;
                font-size: 12px;
                right: 95px;
                top: 15px;
                color: #ffffff;
                padding-left: 8px;
                padding-right: 8px;
                height: 25px;
                line-height: 25px;
                border-radius: 5px;
                background-color: #10ee99;
                font-weight: bold;
            }

            .competition-audience-name {
                position: absolute;
                font-size: 12px;
                right: 170px;
                top: 15px;
                color: #ffffff;
                padding-left: 8px;
                padding-right: 8px;
                height: 25px;
                line-height: 25px;
                border-radius: 5px;
                background-color: #10ee99;
                font-weight: bold;
            }

            .competition-isTeamUp {
                position: absolute;
                font-size: 12px;
                right: 20px;
                top: 50px;
                color: #eeeeee;
                padding-left: 8px;
                padding-right: 8px;
                height: 25px;
                line-height: 25px;
                border-radius: 5px;
                background-color: #a71914b6;
                font-weight: bold;
            }

            .competition-joined-num {
                position: absolute;
                font-size: 12px;
                bottom: 27px;
                left: 145px;
                // font-weight: bold;
                color: #fff;
                text-shadow: 0px 0px 50px #000;

            }

            .join-btn {
                position: absolute;
                width: 100px;
                height: 35px;
                line-height: 35px;
                font-size: 14px;
                background-color: #fff;
                color: #0044ff;
                cursor: pointer;
                bottom: 20px;
                left: 25px;
                text-align: center;
                background-image: linear-gradient(-20deg, #e9defa 0%, #fbfcdb 100%);
                box-shadow: 0 5px 10px #4b6fd1;
            }

            .competition-undertaker3 {
                position: absolute;
                bottom: 63px;
                font-size: 14px;
                color: #fff;
                right: 20px;
                text-shadow: 2px 2px 10px #3b3b3bcc;
                cursor: default;
            }


            .competition-undertaker2 {
                position: absolute;
                bottom: 40px;
                font-size: 14px;
                color: #fff;
                right: 20px;
                text-shadow: 2px 2px 10px #3b3b3bcc;
            }

            .competition-advisor-list-btn {
                position: absolute;
                bottom: 10px;
                font-size: 12px;
                color: #0053a0;
                right: 20px;
                cursor: pointer;
                text-shadow: 0px 0px 10px #f1f1f1cc;
            }
        }

        .competition-details-stage {
            position: absolute;
            top: 380px;
            width: 66%;
            left: 17%;
            height: 100px;
        }

        .competition-details-tags {
            position: absolute;
            top: 520px;
            height: 50px;
            width: 66%;
            left: 17%;

            .competition-information,
            .competition-attachments {
                position: relative;
                height: auto;
                width: 100%;

                .info-title {
                    font-size: 25px;
                    margin-bottom: 10px;
                    font-weight: lighter;
                    color: #000000D9;
                }

                .info-content {
                    margin-bottom: 25px;
                    color: #000000A6;
                    font-size: 16px;
                    text-indent: 24px
                }
            }



            .competition-attachments {
                margin-top: 50px;
                margin-bottom: 150px;

                .attachments-content {
                    margin-bottom: 10px;
                    font-size: 14px;
                    text-indent: 24px
                }
            }

            .competition-joined-view {
                position: relative;
                height: auto;
                width: 100%;
                display: flex;
                flex-wrap: wrap;
                padding: 10px;
                margin-bottom: 250px;

                .competition-joined {
                    position: relative;
                    height: 45px;
                    line-height: 45px;
                    margin-right: 10px;
                    margin-bottom: 10px;
                    width: auto;
                    display: flex;
                    box-shadow: 0px 0px 10px #9494944f;

                    .joined-head {
                        position: relative;
                        width: 30px;
                        height: 30px;
                        border-radius: 50%;
                        top: 7.5px;
                        left: 10px;
                    }

                    .joined-name {
                        position: relative;
                        left: 8px;
                        padding-left: 5px;
                        padding-right: 20px;
                        cursor: default;
                    }
                }

                .competition-joined:hover {
                    box-shadow: 0px 0px 50px #85858560;
                }

            }

            .competition-result-view {
                position: relative;
                width: 100%;
                height: auto;
                margin-bottom: 280px;

                .competition-result-item {
                    position: relative;
                    width: 98%;
                    left: 1%;
                    border: 1px solid #c0c0c07e;
                    height: 50px;
                    line-height: 50px;
                    margin-bottom: 15px;
                    overflow: hidden;

                    .result-file-name {
                        position: absolute;
                        left: 20px;
                        color: #418edb;
                        font-size: 14px;
                        cursor: pointer;
                    }

                    .result-file-name:hover {
                        color: #00b7ff;
                    }

                    .result-download-btn {
                        position: absolute;
                        right: 20px;
                        color: #418edb;
                        font-size: 14px;
                        cursor: pointer;
                    }

                    .result-result-announce-time {
                        position: absolute;
                        right: 80px;
                        color: #acacac;
                        font-size: 13px;
                        cursor: pointer;
                    }
                }
            }

            .tags1-foot {
                position: relative;
                width: 130%;
                left: -15%;
            }
        }

    }

    ::v-deep .el-dialog__body {
        padding: 10px 20px;
    }

    ::v-deep .el-dialog__header {
        padding: 20px 20px 15px;
    }

    .my-team-list {
        position: relative;
        width: 100%;

        .my-team-item {
            position: relative;
            cursor: pointer;
            height: 50px;
            line-height: 50px;
            margin-bottom: 10px;
            padding-left: 20px;
            display: flex;
            overflow: hidden;
            border: 5px dashed #004fb600;

            color: #fafafa;
            font-weight: lighter;

            .team-logo {
                position: relative;

                width: 50px;
                z-index: 1;
            }

            .team-logo2 {
                position: absolute;
                left: -100px;
                top: -500px;
                width: 1000px;
                z-index: 0;
                // 背景模糊100px
                filter: blur(100px) brightness(110%);
            }

            .team-name {
                position: relative;
                margin-left: 0px;
                font-size: 17px;
                font-weight: lighter;
                width: 200px;
                overflow: hidden;
                text-align: center;
                letter-spacing: 1px;
                text-shadow: 0px 0px 5px #000000;
            }

            .team-mem-num {
                position: relative;
                font-size: 15px;
                width: 70px;
                text-align: center;
                text-shadow: 0px 0px 5px #000000;

            }

            .team-label {
                position: relative;
                font-size: 13px;
                width: 180px;
                text-align: right;
                padding-right: 10px;
                text-shadow: 2px 2px 10px #00000093;

                .team-label-item {
                    background-color: #2dc25fde;
                    color: #fff;
                    border-radius: 10px;
                    padding-left: 10px;
                    padding-right: 10px;
                    padding-top: 5px;
                    padding-bottom: 5px;
                    margin-left: 5px;
                }
            }
        }

        .my-team-item-active {
            border: 5px dashed #004fb6;
        }

        .my-team-item-normal {
            border: 5px dashed #004fb600;
        }

        .my-team-item:hover {
            border: 5px dashed #004fb6;
        }
    }
}

/* 标签页CSS */
::v-deep .el-tabs__item.is-active {
    color: #000;
    font-weight: bold;
}

::v-deep .el-tabs__active-bar {
    background-color: #000;
}

::v-deep .el-tabs__item:hover {
    color: #000;
}
</style>