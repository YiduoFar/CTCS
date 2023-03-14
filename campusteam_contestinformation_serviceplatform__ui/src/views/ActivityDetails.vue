<template>
    <div class="activity-details">
        <Nav></Nav>
        <div class="activity-details-body">
            <!-- 面包屑 -->
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/activity_hall' }">活动</el-breadcrumb-item>
                <el-breadcrumb-item>活动详情</el-breadcrumb-item>
            </el-breadcrumb>
            <!-- 步骤条 -->
            <div class="activity-details-stage ">
                <el-steps direction="vertical" :active="activeStage">
                    <el-step :title="item.activityStageName" v-for="item in activityDetailVo.activity.activityStage">
                        <template slot="description">
                            <span>{{ item.activityStageBeginTime }}~{{ item.activityStageEndTime }} <br>
                                {{ item.activityStageIntro }}</span>
                        </template>
                    </el-step>
                </el-steps>
            </div>
            <!-- intro -->
            <div class="activity-details-intro ">
                <img :src="activityDetailVo.activity.activityCover" v-if="activityDetailVo.activity.activityCover" alt=""
                    class="activity-cover">
                <!-- 活动名 -->
                <div class="activity-name">{{ activityDetailVo.activity.activityName }}</div>
                <!-- 活动时间 -->
                <div class="activity-time">时间：{{ activityDetailVo.activity.activityBeginTime }}{{
                    activityDetailVo.activity.activityEndTime == '' ? '' :
                    '~'
                }}{{ activityDetailVo.activity.activityEndTime }}</div>
                <!-- 活动地点 -->
                <div class="activity-place">地点：{{ activityDetailVo.activity.activityPlace }}</div>
                <!-- 活动类型 -->
                <div class="activity-type">
                    类型：{{ activityTypeText[activityDetailVo.activity.activityType] }}（{{ activityDetailVo.activity.isOnline
                        == 0 ? '线下' : '线上' }}）
                </div>
                <!-- 活动参赛队伍人数上下限 -->
                <div class="activity-team-num-limit" v-if="activityDetailVo.activity.isTeamUp == 1">
                    队伍人数要求：{{ activityDetailVo.activity.signUpMinTeamMemberNum }} ~ {{
                        activityDetailVo.activity.signUpMaxTeamMemberNum }} 人
                </div>
                <!-- 发起人 -->
                <el-popover placement="top-start" title="" width="160" trigger="hover"
                    :content="'联系方式: ' + activityDetailVo.initiator.phone">
                    <div slot="reference" class="activity-initiator ">发起人：
                        <img class="initiator-head" :src="activityDetailVo.initiator.userHead" alt="">
                        {{ activityDetailVo.initiator.userName }}
                    </div>
                </el-popover>
                <!-- 实时状态 -->
                <div class="stage-now-bg"></div>
                <div class="stage-now">
                    {{ activityDetailVo.activity.activityStage[activityDetailVo.activity.activityStageNow].activityStageName
                    }}
                </div>
                <!-- 已报名数量 -->
                <div class="activity-joined-num">已有 {{ activityDetailVo.activity.activityJoinedNum }} {{
                    activityDetailVo.activity.isTeamUp == 0 ? '人' :
                    '支队伍'
                }}报名</div>
                <!-- tags -->
                <div class="activity-info-tags ">
                    <!-- 受众 -->
                    <div class="activity-info-tags-item">
                        <span class="item-span ">
                            {{ activityAudienceText[activityDetailVo.activity.activityAudience] }}
                        </span>
                    </div>
                    <!-- 具体受众 -->
                    <div class="activity-info-tags-item" v-if="activityDetailVo.activity.activityAudienceName">
                        <span class="item-span">
                            {{ activityDetailVo.activity.activityAudienceName }}
                        </span>
                    </div>
                    <!-- 综测加分 -->
                    <div class="activity-info-tags-item">
                        <span class="item-span">
                            预计综测加分 {{ activityDetailVo.activity.activityPoints }}
                        </span>
                    </div>
                    <!-- 参加形式 -->
                    <div class="activity-info-tags-item">
                        <span class="item-span bg-red">
                            {{ activityDetailVo.activity.isTeamUp == 0 ? '以个人形式参加' : '以队伍形式参加' }}
                        </span>
                    </div>
                </div>
                <!-- 报名btn -->
                <div class="join-btn" v-if="activityDetailVo.activity.activityJoinedIsOpen == 1" @click="joinActivity()">
                    立即报名</div>
                <div class="join-btn" v-if="activityDetailVo.activity.activityJoinedIsOpen == 0">
                    暂未开放
                </div>
            </div>
            <!-- 已报名LIST -->
            <div class="activity-joined-view ">
                <!-- title -->
                <div class="activity-joined-title">已报名</div>
                <!-- list -->
                <div class="activity-joined-list ">
                    <!-- 无数据 -->
                    <el-empty description="暂无参与者" style="left: 70px;position: relative;" :image-size="100"
                        v-if="nullJoinMem"></el-empty>
                    <!-- 人 -->
                    <img :src="item.userHead" v-for="item in activityDetailVo.joinedUserList" class="joined-list-item"
                        v-show="activityDetailVo.activity.isTeamUp == 0">
                    <!-- 队 -->
                    <div class="joined-list-item2 ts02s" v-for="item in activityDetailVo.joinedTeamList"
                        v-show="activityDetailVo.activity.isTeamUp == 1">
                        <img class="item-team-logo" :src="item.teamLogo" alt="">
                        <div class="item-team-name ">{{ item.teamName }}</div>
                        <div class="item-team-captain ">{{ item.teamCaptionUserName }}</div>
                    </div>
                </div>
            </div>
            <!-- tags -->
            <div class="activity-details-tags">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="活动介绍" name="first">
                        <!-- 1234..v-for打印信息 -->
                        <div class="activity-information"
                            v-for="item, index in activityDetailVo.activity.activityInformationList">
                            <div class="info-title">{{ chineseNum[index] }}、{{ item.infoTitle }}</div>
                            <div class="info-content">
                                {{ item.infoContent }}
                            </div>
                        </div>
                        <Foot class="tags1-foot"></Foot>
                    </el-tab-pane>
                    <el-tab-pane label="资料" name="second">
                        <el-empty description="暂无资料文件" :image-size="150"
                            v-if="activityDetailVo.activityFileList.length == 0"></el-empty>
                        <div class="activity-file-view">
                            <div class="activity-file-item" v-for="item, index in activityDetailVo.activityFileList">
                                <div class="file-name ts02s ">{{ index + 1 }}. {{ item.activityFileName }}</div>
                                <!-- <div class="result-file-url">{{ item.resultFileDownloadURL }}</div> -->
                                <div class="file-announce-time">{{ item.activityFileAnnounceTime.substring(0, 10) }}
                                </div>
                                <div class="file-download-btn" @click="downLoad(item.activityFileUrl)">下载</div>
                            </div>
                        </div>
                        <Foot class="tags1-foot"></Foot>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
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
                    @click="joinActivityForTeam(); visible1 = false">确定</el-button>
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
        activityTypeText: ['团建', '聚会', '运动', '社团', '户外', '游戏'],
        activityAudienceText: ['面向全校', '面向学院', '面向专业', '面向个人'],
        chineseNum: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十'],
        activeName: 'first',
        activityDetailVo: {
            activity: {
                activityStageNow: 0,
                activityStage: [{
                    activityStageName: ''
                }],
            },
            initiator: {},
            // 活动资料
            activityFileList: [],
            // 参与者
            joinedUserList: [],
            // 参与队伍
            joinedTeamList: []
        },
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
    }),
    methods: {
        /**
         * 查询ActivityDetailVo
         */
        ...mapActions('activity', ['selectActivityDetailVoById']),
        /**
         * 查询当前用户的所有用户竞赛关系
         */
        ...mapActions('user_activity', ['selectUserActivity']),
        /**
         * 新增用户竞赛关系UserCompetition
         */
        ...mapActions('user_activity', ['insertUserActivity']),
        /**
         * 新增队伍竞赛关系TeamActivity
         */
        ...mapActions('team_activity', ['insertTeamActivity']),
        /**
         * 根据队长id和竞赛id查询队伍竞赛关系TeamActivity
         */
        ...mapActions('team_activity', ['selectTeamActivityByCaptionIdAndActivityId']),
        /**
         * 通过队长id查询队伍
         */
        ...mapActions('team', ['selectTeamByCaptionId']),
        /**
         * 初始化
         */
        init() {
            this.doGetActivityDetailVo()
        },
        /**
         * 得到活动详情信息
         */
        async doGetActivityDetailVo() {
            let activityId = this.$route.query.activityId
            var formData = new window.FormData()
            formData.append('activityId', activityId)
            const { code, message, data } = await this.selectActivityDetailVoById(formData)
            if (code === 200) {
                let act = { ...data }
                // 处理时间
                act.activity.activityBeginTime = this.$moment(act.activity.activityBeginTime).format('M月D H:mm')
                act.activity.activityEndTime = this.$moment(act.activity.activityEndTime).format('M月D H:mm')
                // 处理步骤
                act.activity.activityStage = [...JSON.parse(act.activity.activityStage)]
                // 循环
                act.activity.activityStage.forEach((item) => {
                    // 如果同一天
                    if (this.$moment(item.activityStageBeginTime).format('YYYY-MM-DD') == this.$moment(item.activityStageEndTime).format('YYYY-MM-DD')) {
                        // format时间为MM.DD H点
                        item.activityStageBeginTime = this.$moment(item.activityStageBeginTime).format('MM.DD H点')
                        if (item.activityStageEndTime) {
                            item.activityStageEndTime = this.$moment(item.activityStageEndTime).format('H点')
                        }
                    } else {
                        // 如果不同一天
                        // format时间为MM-DD
                        item.activityStageBeginTime = this.$moment(item.activityStageBeginTime).format('MM.DD')
                        // 如果无结束时间则不format
                        if (item.activityStageEndTime) {
                            item.activityStageEndTime = this.$moment(item.activityStageEndTime).format('MM.DD')
                        }
                    }
                })
                // 反序列化活动内容
                act.activity.activityInformationList = [...JSON.parse(act.activity.activityInformationList)]
                this.activityDetailVo = { ...act }

            }
            // 如果竞赛是组队报名，则获取以当前登录用户为首的队伍以供选择
            if (this.activityDetailVo.activity.isTeamUp == 1) {
                let userId = localStorage.getItem('userId')
                const { code, message, data } = await this.selectTeamByCaptionId(userId)
                if (code === 200 && data != null) {
                    this.teamList = [...data]
                    this.teamList.forEach((item) => {
                        item.teamLabel = JSON.parse(item.teamLabel)
                    })
                }
            }
        },
        /**
         * 文件下载
         * @param {*} url 
         */
        downLoad(url) {
            window.open(url, '_blank')
        },
        handleClick(tab, event) {
            
        },
        /**
         * 参加活动
         */
        async joinActivity() {
            // 个人参加
            if (this.activityDetailVo.activity.isTeamUp == 0) {
                // 构造userActivity
                let userActivity = {
                    userId: localStorage.getItem('userId'),
                    activityId: this.activityDetailVo.activity.activityId,
                    status: 0
                }
                // 检查是否已经参加
                const { code, message, data } = await this.selectUserActivity(userActivity)
                if (code === 200) {
                    // data不为null，则已经提交申请或参加活动
                    if (data != null) {
                        ElementUI.Message.warning(data.status == 0 ? '您已提交报名申请' : '您已参加')
                        return
                    }
                }
                let _this = this
                this.$confirm(_this.activityDetailVo.activity.activityName, '确定参加活动?', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(async () => {
                    // 参加活动，新增记录
                    const { code, message, data } = await _this.insertUserActivity(userActivity)
                    if (code === 200) {
                        ElementUI.Message.success('报名成功')
                    }
                }).catch(() => { })
            }
            // 队伍参加
            if (this.activityDetailVo.activity.isTeamUp == 1) {
                // 构造检查Vo
                let vo = {
                    userId: localStorage.getItem('userId'),
                    activityId: this.activityDetailVo.activity.activityId
                }
                // 检查自己是否已经有队伍参加了这个活动
                const { code, message, data } = await this.selectTeamActivityByCaptionIdAndActivityId(vo)
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
         * 队伍申请参加活动
         */
        async joinActivityForTeam() {
            let teamActivity = {
                teamId: this.chooseTeam.teamId,
                activityId: this.activityDetailVo.activity.activityId,
                status: 0
            }
            const { code, message, data } = await this.insertTeamActivity(teamActivity)
            if (code === 200) {
                ElementUI.Message.success('报名成功')
            }
        },
    },
    created() {
        this.init()
    },
    computed: {
        /**
         * 当前步骤
         */
        activeStage() {
            return parseInt(this.activityDetailVo.activity.activityStageNow) + 1
        },
        nullJoinMem() {
            if (this.activityDetailVo.joinedUserList == null) {
                return this.activityDetailVo.joinedTeamList.length == 0
            }
            if (this.activityDetailVo.joinedTeamList == null) {
                return this.activityDetailVo.joinedUserList.length == 0
            }
        }
    },
    components: {
        Nav, Foot
    },
}
</script>

<style lang="less" scoped>
.activity-details {
    width: 100%;
    height: auto;

    .activity-details-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;
        background-color: #ffffff;

        .activity-details-stage {
            position: absolute;
            top: 150px;
            left: 325px;
            width: 200px;
            height: 400px;
            overflow-y: scroll;
            overflow-x: hidden;
        }

        .activity-details-intro {
            position: absolute;
            top: 150px;
            left: 540px;
            width: 740px;
            height: 370px;
            box-shadow: 0 0 10px #7c7c7c93;
            background-image: linear-gradient(-45deg, #e9f5f7 0%, #ffffff 90%, white 100%);
            // overflow: hidden;
            // background-image: linear-gradient(-225deg, #5D9FFF 0%, #B8DCFF 48%, #6BBBFF 100%);

            .activity-cover {
                position: relative;
                width: 100%;
                height: 100%;
                // 背景模糊100px
                filter: blur(1000px) brightness(180%);
                // filter: blur(5px);
            }

            .activity-name {
                position: absolute;
                font-size: 38px;
                left: 50px;
                top: 20px;
                font-weight: bold;
                color: #383838a8;
            }

            .activity-time {
                position: absolute;
                font-size: 14px;
                left: 50px;
                top: 90px;
                color: #383838af;
            }

            .activity-type {
                position: absolute;
                font-size: 14px;
                left: 50px;
                top: 140px;
                color: #383838af;
            }

            .activity-team-num-limit{
                position: absolute;
                font-size: 14px;
                left: 50px;
                top: 165px;
                color: #383838af;
            }

            .activity-place {
                position: absolute;
                font-size: 14px;
                left: 50px;
                top: 115px;
                color: #383838af;
            }

            .activity-initiator {
                position: absolute;
                top: 205px;
                font-size: 14px;
                color: #383838af;
                left: 50px;

                .initiator-head {
                    position: relative;
                    top: 5px;
                    width: 25px;
                    height: 25px;
                    border-radius: 50%;
                }
            }


            .stage-now-bg {
                position: absolute;
                top: 0.8px;
                right: -6.5px;
                border-bottom: 25px solid #349dff34;
                border-left: 95px solid transparent;
                border-right: 7.8px solid transparent;

                width: 90px;

                -moz-transform: rotate(15deg);
                -webkit-transform: rotate(15deg);
                -o-transform: rotate(15deg);
                transform: rotate(15deg);
                filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
                // height: 30px;
            }

            .stage-now {
                position: absolute;
                top: 12px;
                right: 19px;
                font-size: 18px;
                color: #fff;
                font-weight: bold;
                -moz-transform: rotate(15deg);
                -webkit-transform: rotate(15deg);
                -o-transform: rotate(15deg);
                transform: rotate(15deg);
                filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
                text-shadow: 0 0 5px #349dffad;
            }

            .activity-info-tags {
                position: absolute;
                // right: 20px;
                // top: 15px;
                bottom: 30px;
                right: 30px;
                height: auto;
                width: auto;

                .activity-info-tags-item {
                    position: relative;
                    width: auto;
                    text-align: right;
                    height: 35px;
                    line-height: 35px;

                    .item-span {
                        font-size: 12px;
                        color: #ffffff;

                        padding-top: 5px;
                        padding-bottom: 5px;
                        padding-left: 8px;
                        padding-right: 8px;

                        border-radius: 5px;
                        font-weight: bold;
                        background-color: #10ee99;
                    }

                    .bg-red {
                        background-color: #df5941;
                    }
                }
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
                bottom: 30px;
                left: 52px;
                text-align: center;
                background-image: linear-gradient(-20deg, #def2fa 0%, #fbfcdb 100%);
                // background-image: linear-gradient(to top, #d5d4d0 0%, #d5d4d0 1%, #eeeeec 31%, #efeeec 75%, #e9e9e7 100%);
                box-shadow: 0 5px 10px #4b6fd1;
            }

            .activity-joined-num {
                position: absolute;
                bottom: 36px;
                left: 170px;
                color: #383838af;
                font-size: 12px;
            }
        }

        .activity-joined-view {
            position: absolute;
            top: 150px;
            left: 1300px;
            width: 290px;
            height: 370px;
            background-color: #ffffff94;
            // border-radius: 20px;
            box-shadow: 0 0 10px #6969692d;

            .activity-joined-title {
                position: absolute;
                font-size: 18px;
                top: 15px;
                left: 27px;
                color: #7580859a;
                font-weight: bold;
            }

            .activity-joined-list {
                position: absolute;
                top: 50px;
                left: 20px;
                max-height: 310px;
                width: 250px;
                overflow: scroll;
                display: flex;
                flex-wrap: wrap;

                .joined-list-item {
                    position: relative;
                    width: 50px;
                    height: 50px;
                    margin: 5px;
                    border-radius: 50%;
                }

                .joined-list-item2 {
                    position: relative;
                    width: 100%;
                    height: 40px;
                    line-height: 40px;
                    // border: 1px solid #dddddd;
                    // background-color: #dfdfdfda;
                    border-radius: 10px;
                    margin-bottom: 10px;
                    color: #ffffff;
                    font-weight: bold;
                    text-shadow: 0 0 20px #7e8eb4;
                    cursor: pointer;
                    background-image: linear-gradient(120deg, #cadaff 0%, #f8babc 100%);

                    .item-team-logo {
                        position: absolute;
                        width: 30px;
                        height: 30px;
                        left: 110px;
                        top: 5px;
                    }

                    .item-team-name {
                        position: absolute;
                        width: 105px;
                        text-align: right;
                        font-size: 14px;
                        left: 0px;
                    }

                    .item-team-captain {
                        position: absolute;
                        width: 100px;
                        text-align: left;
                        right: 0px;
                        font-size: 14px;
                    }
                }

                .joined-list-item2:hover {
                    box-shadow: 0 0 10px #afafafd9;
                    border-radius: 0px;
                    background-image: linear-gradient(120deg, #b0c6ff 0%, #ff979a 100%);
                }
            }
        }

        .activity-details-tags {
            position: absolute;
            top: 580px;
            height: 50px;
            width: 66%;
            left: 17%;

            .activity-information {
                position: relative;
                height: auto;
                width: 100%;

                .info-title {
                    font-size: 20px;
                    margin-bottom: 10px;
                    color: #000000D9;
                }

                .info-content {
                    margin-bottom: 25px;
                    color: #000000A6;
                    font-size: 16px;
                    text-indent: 24px
                }
            }

            .activity-file-view {
                position: relative;
                width: 100%;
                height: auto;
                margin-bottom: 280px;

                .activity-file-item {
                    position: relative;
                    width: 98%;
                    left: 1%;
                    border: 1px solid #c0c0c07e;
                    // border-radius: 10px;
                    height: 50px;
                    line-height: 50px;
                    margin-bottom: 15px;
                    // padding-left: 20px;
                    // padding-right: 20px;
                    overflow: hidden;

                    .file-name {
                        position: absolute;
                        left: 20px;
                        // width: 500px;
                        color: #418edb;
                        font-size: 14px;
                        cursor: pointer;
                    }

                    .file-name:hover {
                        color: #00b7ff;
                    }

                    .file-download-btn {
                        position: absolute;
                        right: 20px;
                        color: #418edb;
                        font-size: 14px;
                        cursor: pointer;
                    }

                    .file-announce-time {
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
                margin-top: 100px;
            }
        }
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
                filter: blur(100px) brightness(150%);
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
                text-shadow: 2px 2px 10px #00000093;
            }

            .team-mem-num {
                position: relative;
                font-size: 15px;
                width: 70px;
                text-align: center;
                text-shadow: 2px 2px 10px #00000093;
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

/* 定义滚动条样式 */
::-webkit-scrollbar {
    width: 4px;
    height: 6px;
    background-color: rgba(240, 240, 240, 0);
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0);
    border-radius: 10px;
    background-color: rgba(240, 240, 240, 0);
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
    border-radius: 10px;
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0.5);
    background-color: rgba(158, 158, 158, 0);
}
</style>