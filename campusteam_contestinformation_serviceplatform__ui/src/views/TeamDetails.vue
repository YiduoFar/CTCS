<template>
    <div class="team-details">
        <Nav></Nav>
        <div class="team-details-body">
            <!-- 左边 -->
            <div class="view-left ">
                <!-- 队伍信息 -->
                <!-- logo -->
                <img :src="team.teamLogo" alt="" class="team-logo">
                <!-- name -->
                <div class="team-name ">{{ team.teamName }}</div>
                <!-- 人数 -->
                <div class="team-mem-num">{{ team.teamMemberNum }} / {{ team.teamMemberNumMax }}</div>
                <!-- 宣言 -->
                <div class="team-intro ">
                    <div class="team-intro-text ">
                        {{ team.teamIntro }}
                    </div>
                    <div class="line1"></div>
                </div>
                <!-- 招新状态 -->
                <div class="team-status ">
                    <span class="team-status-text">队伍状态：</span>
                    <span class="team-status-content">{{ team.teamStatus == 0 ? '招新关闭' : '正在招新中' }}</span>
                </div>
                <!-- 创建时间 -->
                <div class="team-create-time ">
                    <span>创建时间：</span>
                    <span class="team-create-content">{{ $moment(team.teamCreateTime).format('YYYY.MM.DD') }}</span>
                </div>
                <!-- 队长 -->
                <div class="team-captain ">
                    <!-- title -->
                    <div class="captain-title">Leader</div>
                    <div class="line2"></div>
                    <!-- 队长头像 -->
                    <img :src="captain.userHead" alt="" class="captain-head">
                    <!-- 队长昵称 -->
                    <div class="captain-name">
                        <div class="name-content">
                            {{ captain.userName }}
                        </div>
                        <!-- 队长性别 -->
                        <div class="captain-sex">
                            <i class="el-icon-male" v-if="captain.sex == 1" style="color:#409EFF;"></i>
                            <i class="el-icon-female" v-if="captain.sex == 0" style="color:#F56C6C;"></i>
                        </div>
                    </div>
                    <!-- 队长签名 -->
                    <div class="captain-intro ">{{ captain.intro }}</div>
                    <!-- ... -->
                </div>
                <!-- 成员 -->
                <div class="team-member">
                    <!-- title -->
                    <div class="member-title">Member</div>
                    <div class="line3"></div>
                    <!-- 打印 -->
                    <div class="member-list ">
                        <div class="no-member" v-if="memberList.length == 0" style="font-size:14px;color:#909399;">
                            （暂无成员）</div>
                        <div class="member-item" v-for="item in memberList">
                            <!-- 成员头像 -->
                            <img :src="item.userHead" alt="" class="member-head">
                            <!-- 成员昵称 -->
                            <div class="member-name">
                                <div class="name-content">
                                    {{ item.userName }}
                                </div>
                                <!-- 成员性别 -->
                                <div class="member-sex">
                                    <i class="el-icon-male" v-if="item.sex == 1" style="color:#409EFF;"></i>
                                    <i class="el-icon-female" v-if="item.sex == 0" style="color:#F56C6C;"></i>
                                </div>
                            </div>
                            <!-- 成员签名 -->
                            <div class="member-intro">{{ item.intro }}</div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 中间 -->
            <div class="view-middle ">
                <!-- 参加的竞赛 -->
                <div class="joined-competition ">
                    <div class="j-c-title">参加的竞赛</div>
                    <div class="j-c-list ">
                        <el-empty description="暂无比赛" :image-size="100" v-if="competitionList.length == 0"></el-empty>
                        <div class="j-c-item ts02s" v-for="item in competitionList"
                            @click="$router.push({ path: '/competition_details', query: { competitionId: item.competitionId } })">
                            <!-- name -->
                            <div class="j-c-name ts02s">{{ item.competitionName }}</div>
                            <!-- status -->
                            <div class="j-c-status">
                                <span style="color: #909399;" v-if="item.competitionStatus == 0">已结束</span>
                                <span style="color: #67C23A;" v-if="item.competitionStatus == 1">进行中</span>
                                <!-- {{ item.competitionStatus == 0 ? '已结束' : '进行中' }} -->
                            </div>
                            <!-- time -->
                            <div class="j-c-time">{{ $moment(item.competitionBeginTime).format('YYYY.MM.DD') }}</div>
                            <!-- audience -->
                            <div class="j-c-audience">{{
                                item.competitionAudience == 0 ? '面向全校' :
                                item.competitionAudience == 1 ? '面向学院' :
                                    item.competitionAudience == 2 ? '面向专业' : '面向个人'
                            }}</div>
                        </div>
                    </div>
                </div>
                <!-- 参加的活动 -->
                <div class="joined-activity ">
                    <div class="j-a-title">参加的活动</div>
                    <div class="j-a-list ">
                        <el-empty description="暂无活动" :image-size="100" v-if="activityList.length == 0"></el-empty>
                        <div class="j-a-item ts02s" v-for="item in activityList"
                            @click="$router.push({ path: '/activity_details', query: { activityId: item.activityId } })">
                            <!-- name -->
                            <div class="j-a-name ts02s">{{ item.activityName }}</div>
                            <!-- status -->
                            <div class="j-a-status">
                                <span style="color: #909399;" v-if="item.activityStatus == 0">已结束</span>
                                <span style="color: #67C23A;" v-if="item.activityStatus == 1">进行中</span>
                                <!-- {{ item.competitionStatus == 0 ? '已结束' : '进行中' }} -->
                            </div>
                            <!-- time -->
                            <div class="j-a-time">{{ $moment(item.activityBeginTime).format('YYYY.MM.DD') }}</div>
                            <!-- audience -->
                            <div class="j-a-audience">{{
                                item.activityAudience == 0 ? '面向全校' :
                                item.activityAudience == 1 ? '面向学院' :
                                    item.activityAudience == 2 ? '面向专业' : '面向个人'
                            }}</div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 右边 -->
            <div class="view-right ">
                <!-- 标签 -->
                <div class="team-field-label">
                    <div class="label-title">标签</div>
                    <div class="label-list ">
                        <div class="label-item " v-for="item in JSON.parse(team.teamLabel)">{{ item }}</div>
                    </div>
                </div>
                <!-- 星级 -->
                <div class="team-star">
                    <div class="star-title">星级</div>
                    <el-rate class="star-content" v-model="team.teamStar" disabled show-score text-color="#ff9900"
                        score-template="{value}">
                    </el-rate>
                </div>
                <!-- 成就 -->
                <div class="team-achievements">
                    <div class="achievements-title">成就</div>
                    <div class="achievements-left">
                        <div class="joined-com-num-text">竞赛数</div>
                        <div class="joined-com-num">{{ competitionList.length }}
                            <span style="font-size: 14px;font-weight: normal;">场</span>
                        </div>
                    </div>
                    <div class="achievements-right">
                        <div class="joined-act-num-text">活动数</div>
                        <div class="joined-act-num">{{ activityList.length }}
                            <span style="font-size: 14px;font-weight: normal;">个</span>
                        </div>
                    </div>
                </div>
                <!-- 加入 -->
                <div class="join-btn ts05s" v-if="team.teamStatus == 1" @click="doJoinTeam()">加入我们</div>
            </div>
            <Foot class="team-details-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import { mapActions } from 'vuex'
import ElementUI from 'element-ui'
export default {
    data: () => ({
        teamLabel: ['Java', '编程', '硬件'], // 根据属性teamLabel前端计算得到
        team: {
            teamLogo: '2',
            teamName: 'Fate/Stay Night',
            teamIntro: '吾剑之所指，吾心之所向',
            teamStatus: '0', // 0不招新 1招新
            teamCreateTime: '2020-03-02',
            teamMemberNumNow: '6',
            teamMemberNumLimit: '20',
            teamCaptainId: '1',
            teamStar: '3.5',
            teamLabel: ''
        },
        captain: {
            userId: '1',
            userName: 'Yiduofar',
            userHead: '',
            userIntro: '0.0 划水大队长',
            userSex: '1', // 0女 1男
        },
        memberList: [{
            userId: '2',
            userName: 'Yiduo',
            userHead: '',
            userIntro: '0.0 划水大队',
            userSex: '0', // 0女 1男
        }, {
            userId: '3',
            userName: 'Yiduo',
            userHead: '',
            userIntro: '0.0 划水',
            userSex: '0', // 0女 1男
        }, {
            userId: '4',
            userName: 'Yiduo',
            userHead: '',
            userIntro: '0.0 划',
            userSex: '0', // 0女 1男
        }, {
            userId: '5',
            userName: 'Yiduo',
            userHead: '',
            userIntro: '0.0',
            userSex: '0', // 0女 1男
        },],
        competitionList: [{
            competitionName: '第十四届蓝桥杯大赛广东省选拔赛',
            competitionBeginTime: '2020-03-02',
            competitionAudience: '0',
            competitionStatus: '1',
        }, {
            competitionName: '第十四届蓝桥杯大赛广东省选拔赛',
            competitionBeginTime: '2020-03-02',
            competitionAudience: '0',
            competitionStatus: '1',
        }, {
            competitionName: '第十四届蓝桥杯大赛广东省选拔赛',
            competitionBeginTime: '2020-03-02',
            competitionAudience: '0',
            competitionStatus: '1',
        }, {
            competitionName: '第十四届蓝桥杯大赛广东省选拔赛',
            competitionBeginTime: '2020-03-02',
            competitionAudience: '0',
            competitionStatus: '1',
        },],
        activityList: [{
            activityName: '周四图书馆团建大扫除',
            activityBeginTime: '2020-03-02',
            activityAudience: '0',
            activityStatus: '1',
        }, {
            activityName: '周四图书馆团建大扫除',
            activityBeginTime: '2020-03-02',
            activityAudience: '0',
            activityStatus: '1',
        }, {
            activityName: '周四图书馆团建大扫除',
            activityBeginTime: '2020-03-02',
            activityAudience: '0',
            activityStatus: '1',
        }, {
            activityName: '周四图书馆团建大扫除',
            activityBeginTime: '2020-03-02',
            activityAudience: '0',
            activityStatus: '1',
        }, {
            activityName: '周四图书馆团建大扫除',
            activityBeginTime: '2020-03-02',
            activityAudience: '0',
            activityStatus: '1',
        }, {
            activityName: '周四图书馆团建大扫除',
            activityBeginTime: '2020-03-02',
            activityAudience: '0',
            activityStatus: '1',
        },],
    }),
    methods: {
        ...mapActions('team', ['selectTeamDetailVoById']),
        ...mapActions('user_team', ['selectAndJudgeOne']),
        ...mapActions('user_team', ['insertUserTeam']),
        async init() {
            const { code, message, data } = await this.selectTeamDetailVoById(this.$route.query.teamId)
            if (code === 200) {
                this.team = { ...data.team }
                this.captain = { ...data.captain }
                this.memberList = [...data.memberList]
                this.competitionList = [...data.competitionList]
                this.activityList = [...data.activityList]
            }
        },
        /* 申请加入队伍 */
        async doJoinTeam() {
            if (await this.beforeJoinTeam()) {
                this.$confirm(this.team.teamName, '申请加入队伍', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let userTeam = {
                        userId: localStorage.getItem('userId'),
                        teamId: this.team.teamId,
                        status: 0
                    }
                    const { code, message, data } = await this.insertUserTeam(userTeam)
                    if (code === 200) {
                        ElementUI.Message.success('已发送申请')
                    }
                }).catch(() => {

                })
            }
        },
        async beforeJoinTeam() {
            let judgeVo = {
                userId: localStorage.getItem('userId'),
                teamId: this.team.teamId
            }
            const { code, message, data } = await this.selectAndJudgeOne(judgeVo)
            return code === 200
        }
    },
    created() {
        this.init()
    },
    components: {
        Nav, Foot
    },
}
</script>

<style lang="less" scoped>
.team-details {
    width: 100%;
    height: auto;

    .team-details-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;
        background-color: #ffffff;

        .view-left {
            position: absolute;
            width: 15%;
            height: 900px;
            overflow: hidden;
            top: 90px;
            left: 280px;

            .team-logo {
                position: absolute;
                width: 60px;
            }

            .team-name {
                position: absolute;
                width: 180px;
                left: 75px;
                top: 5px;
                overflow: hidden;
                font-size: 18px;
            }

            .team-mem-num {
                position: absolute;
                width: 50px;
                font-size: 12px;
                left: 75px;
                top: 35px;
                color: #909399;
            }

            .team-intro {
                position: absolute;
                width: 100%;
                top: 70px;

                .team-intro-text {
                    position: relative;
                    font-size: 14px;
                    color: #909399;
                    margin-bottom: 10px;

                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 2;
                    -webkit-box-orient: vertical;
                }

                .line1 {
                    position: relative;
                    border-bottom: 1px solid #90939941;
                }
            }

            .team-status {
                position: absolute;
                width: 100%;
                top: 120px;
                font-size: 14px;
                color: #909399;
                height: 20px;
                line-height: 20px;

                .team-status-content {
                    position: absolute;
                    right: 5px;
                }
            }

            .team-create-time {
                position: absolute;
                width: 100%;
                top: 145px;
                font-size: 14px;
                color: #909399;
                height: 20px;
                line-height: 20px;

                .team-create-content {
                    position: absolute;
                    right: 5px;
                }
            }

            .team-captain {
                position: absolute;
                width: 100%;
                top: 200px;
                height: 100px;

                .captain-title {
                    font-size: 14px;
                    // font-weight: bold;
                    color: #909399;
                }

                .line2 {
                    position: absolute;
                    border-top: 1px solid #90939941;
                    width: 100%;
                    top: 25px;
                }

                .captain-head {
                    position: absolute;
                    top: 40px;
                    width: 50px;
                    border-radius: 50%;
                }

                .captain-name {
                    position: absolute;
                    font-size: 18px;

                    left: 65px;
                    top: 40px;
                    color: #000000;
                    font-weight: lighter;
                    display: flex;

                    .name-content {
                        position: relative;
                        margin-right: 10px;
                    }
                }

                .captain-intro {
                    position: absolute;
                    font-size: 14px;
                    color: #909399;
                    left: 65px;
                    top: 70px;
                    width: 220px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }
            }

            .team-member {
                position: absolute;
                width: 100%;
                top: 330px;
                height: 550px;

                .member-title {
                    font-size: 14px;
                    // font-weight: bold;
                    color: #909399;
                }

                .line3 {
                    position: absolute;
                    border-top: 1px solid #90939941;
                    width: 100%;
                    top: 25px;
                }

                .member-list {
                    position: absolute;
                    top: 40px;
                    width: 100%;

                    .member-item {
                        position: relative;
                        width: 100%;
                        height: 60px;
                        margin-bottom: 10px;

                        .member-head {
                            position: absolute;
                            top: 5px;
                            width: 50px;
                            border-radius: 50%;
                        }

                        .member-name {
                            position: absolute;
                            font-size: 18px;

                            left: 65px;
                            top: 5px;
                            color: #000000;
                            font-weight: lighter;
                            display: flex;

                            .name-content {
                                position: relative;
                                margin-right: 10px;
                            }
                        }

                        .member-intro {
                            position: absolute;
                            font-size: 14px;
                            color: #909399;
                            left: 65px;
                            top: 35px;
                            width: 220px;
                            // 省略效果
                            overflow: hidden;
                            display: -webkit-box;
                            /*第几行裁剪*/
                            -webkit-line-clamp: 1;
                            -webkit-box-orient: vertical;
                        }
                    }
                }


            }
        }

        .view-middle {
            position: absolute;
            width: 38.9%;
            height: 900px;
            overflow: hidden;
            top: 90px;
            left: 585px;

            .joined-competition {
                width: 100%;
                // height: 400px;
                margin-bottom: 20px;
                overflow: hidden;

                .j-c-title {
                    width: 100%;
                    font-size: 20px;
                    font-weight: lighter;
                    padding-bottom: 10px;
                    border-bottom: 1px solid #90939941;
                }

                .j-c-list {
                    width: 100%;
                    min-height: 110px;
                    max-height: 350px;
                    overflow: scroll;
                    position: relative;
                    top: 10px;

                    .j-c-item {
                        position: relative;
                        width: 98%;
                        height: 50px;
                        left: 1%;
                        margin-top: 2px;
                        margin-bottom: 5px;
                        // cursor: pointer;
                        line-height: 50px;

                        .j-c-name {
                            position: absolute;
                            font-size: 14px;
                            color: #45759c;
                            left: 20px;
                            cursor: pointer;
                        }

                        .j-c-name:hover {
                            color: #6599c4;
                            // color: #65c482;
                        }

                        .j-c-status {
                            position: absolute;
                            font-size: 14px;
                            // color: #949494;
                            left: 380px;
                        }

                        .j-c-time {
                            position: absolute;
                            font-size: 14px;
                            color: #949494;
                            left: 500px;
                        }

                        .j-c-audience {
                            position: absolute;
                            font-size: 14px;
                            color: #949494;
                            left: 650px;
                        }
                    }

                    .j-c-item:hover {
                        box-shadow: 0 0 1px #020202;
                        // border: 1px solid #909399;
                    }
                }
            }

            .joined-activity {
                width: 100%;
                // height: 430px;

                .j-a-title {
                    width: 100%;
                    font-size: 20px;
                    font-weight: lighter;
                    padding-bottom: 10px;
                    border-bottom: 1px solid #90939941;
                }

                .j-a-list {
                    width: 100%;
                    min-height: 110px;
                    max-height: 500px;
                    overflow: scroll;
                    position: relative;
                    top: 10px;

                    .j-a-item {
                        position: relative;
                        width: 98%;
                        height: 50px;
                        left: 1%;
                        margin-top: 2px;
                        margin-bottom: 5px;
                        // cursor: pointer;
                        line-height: 50px;

                        .j-a-name {
                            position: absolute;
                            font-size: 14px;
                            color: #45759c;
                            left: 20px;
                            cursor: pointer;
                        }

                        .j-a-name:hover {
                            color: #6599c4;
                            // color: #65c482;
                        }

                        .j-a-status {
                            position: absolute;
                            font-size: 14px;
                            // color: #949494;
                            left: 380px;
                        }

                        .j-a-time {
                            position: absolute;
                            font-size: 14px;
                            color: #949494;
                            left: 500px;
                        }

                        .j-a-audience {
                            position: absolute;
                            font-size: 14px;
                            color: #949494;
                            left: 650px;
                        }
                    }

                    .j-a-item:hover {
                        box-shadow: 0 0 1px #020202;
                        // border: 1px solid #909399;
                    }
                }
            }
        }

        .view-right {
            position: absolute;
            width: 14.5%;
            height: 900px;
            overflow: hidden;
            top: 90px;
            right: 280px;

            .team-star {
                position: relative;
                display: flex;
                width: 98%;
                left: 1%;
                border: 1px solid #90939948;
                border-radius: 5px;
                height: 40px;
                line-height: 40px;
                margin-bottom: 15px;

                .star-title {
                    position: absolute;
                    left: 15px;
                    font-size: 15px;
                    font-weight: lighter;
                }

                .star-content {
                    position: absolute;
                    right: 15px;
                    top: 10px;
                }
            }

            .team-achievements {
                position: relative;
                width: 98%;
                left: 1%;
                border: 1px solid #90939948;
                border-radius: 2px;
                height: 150px;
                overflow: hidden;
                margin-bottom: 15px;

                .achievements-title {
                    position: absolute;
                    left: 15px;
                    top: 10px;
                    font-size: 15px;
                    // font-weight: lighter;
                }

                .achievements-left {
                    position: absolute;
                    left: 0;
                    top: 50px;
                    width: 50%;
                    height: 100px;
                    border-top: 1px solid #9093992d;
                    border-right: 1px solid #9093992d;

                    .joined-com-num-text {
                        position: absolute;
                        top: 25px;
                        width: 100%;
                        text-align: center;
                        font-size: 14px;
                    }

                    .joined-com-num {
                        position: absolute;
                        top: 50px;
                        width: 100%;
                        text-align: center;
                        font-size: 18px;
                        font-weight: bold;
                    }
                }

                .achievements-right {
                    position: absolute;
                    right: 0;
                    top: 50px;
                    width: 50%;
                    height: 100px;
                    border-top: 1px solid #9093992d;

                    .joined-act-num-text {
                        position: absolute;
                        top: 25px;
                        width: 100%;
                        text-align: center;
                        font-size: 14px;
                    }

                    .joined-act-num {
                        position: absolute;
                        top: 50px;
                        width: 100%;
                        text-align: center;
                        font-size: 18px;
                        font-weight: bold;
                    }
                }
            }

            .team-field-label {
                position: relative;
                display: flex;
                width: 98%;
                left: 1%;
                border: 1px solid #90939948;
                border-radius: 5px;
                height: 75px;
                margin-bottom: 15px;

                .label-title {
                    position: absolute;
                    left: 15px;
                    top: 10px;
                    font-size: 15px;
                    font-weight: lighter;
                }

                .label-list {
                    position: absolute;
                    width: 90%;
                    left: 5%;
                    top: 38px;
                    height: 25px;
                    display: flex;

                    // justify-content: flex-end;
                    .label-item {
                        height: 25px;
                        line-height: 25px;
                        font-size: 12px;
                        padding-left: 8px;
                        padding-right: 8px;
                        margin-right: 5px;
                        border-radius: 5px;
                        background-color: #27c268;
                        color: #fff;
                    }
                }


            }

            .join-btn {
                position: relative;
                width: 100%;
                height: 40px;
                line-height: 40px;
                background-color: #1ca751;
                color: #fff;
                border-radius: 5px;
                text-align: center;
                font-size: 16px;
                font-weight: lighter;
                cursor: pointer;
            }

            .join-btn:hover {
                background-color: #24c963;
            }
        }

        .team-details-foot {
            position: absolute;
            top: 1100px;
            background-color: #00000000;
        }
    }
}
</style>