<template>
    <div class="team-details">
        <Nav></Nav>
        <div class="team-details-body">
            <!-- 左边 -->
            <div class="view-left ">
                <!-- 个人信息 -->
                <!-- 头像 -->
                <img :src="user.userHead" alt="" class="user-head" v-if="user.status != 0">
                <img src="../assets/img/图标/空白头像.png" alt="" class="user-head" v-if="user.status == 0 || !user.userHead">
                <!-- name -->
                <div class="user-name ">
                    <span class="user-name-content ">
                        {{ user.status == 0 ? '此用户已注销' : user.userName }}
                    </span>
                    <span class="user-status" v-if="user.status == 2">(该用户已封禁)</span>
                </div>
                <!-- 性别 -->
                <div class="user-sex">
                    <i class="el-icon-male" v-if="user.sex == 1" style="color:#409EFF;"></i>
                    <i class="el-icon-female" v-if="user.sex == 0" style="color:#F56C6C;"></i>
                </div>
                <!-- 身份 -->
                <div class="user-identity">
                    {{ user.identity == 0 ? '学生' : user.identity == 1 ? '教师' : '管理员' }}
                </div>
                <!-- 签名 -->
                <div class="user-intro ">
                    <div class="user-intro-text ">
                        {{ user.intro }}
                    </div>
                    <div class="line1"></div>
                </div>
                <!-- 学院 -->
                <div class="user-collage ">
                    <span>学院：</span>
                    <span class="user-collage-content">{{ user.college }}</span>
                </div>
                <!-- 专业班级 -->
                <div class="user-specialty-clazz ">
                    <span>专业班级：</span>
                    <span class="user-specialty-clazz-content">{{ user.specialty }}{{ user.clazz }}</span>
                </div>
                <!-- 参加的队伍 -->
                <div class="joined-team">
                    <div class="j-t-title">他加入的队伍</div>
                    <div class="line2"></div>
                    <div class="joined-team-list">
                        <el-empty description="暂无组队" :image-size="100" v-if="joinedTeamList.length == 0"></el-empty>
                        <div class="joined-team-item" v-for="item in joinedTeamList">
                            <!-- logo -->
                            <img :src="item.teamLogo" class="j-t-logo" alt="">
                            <!-- name -->
                            <div class="j-t-name">
                                <div class="name-content">
                                    {{ item.teamName }}
                                </div>
                                <div class="j-t-mem-num">{{ item.teamMemberNum }}/{{ item.teamMemberNumMax }}
                                </div>
                            </div>
                            <!-- intro -->
                            <div class="j-t-intro">{{ item.teamIntro }}</div>
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
                        <el-empty description="暂无比赛" :image-size="100" v-if="joinedCompetitionList.length == 0"></el-empty>
                        <div class="j-c-item ts02s" v-for="item in joinedCompetitionList"
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
                            <div class="j-c-time">{{ $moment(item.competitionBeginTime).format('YYYY.MM.DD HH:mm') }}</div>
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
                        <el-empty description="暂无活动" :image-size="100" v-if="joinedActivityList.length == 0"></el-empty>
                        <div class="j-a-item ts02s" v-for="item in joinedActivityList"
                            @click="$router.push({ path: '/activity_details', query: { activityId: item.activityId } })">
                            <!-- name -->
                            <div class="j-a-name ts02s">{{ item.activityName }}</div>
                            <!-- status -->
                            <div class="j-a-status">
                                <span style="color: #909399;" v-if="item.activityStatus == 0">已结束</span>
                                <span style="color: #67C23A;" v-if="item.activityStatus == 1">进行中</span>
                            </div>
                            <!-- time -->
                            <div class="j-a-time">{{ $moment(item.activityBeginTime).format('YYYY.MM.DD HH:mm') }}</div>
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
                <!-- 成就 -->
                <div class="user-achievements">
                    <div class="achievements-title">成就</div>
                    <!-- flex -->
                    <div class="ach-flex ">
                        <div class="achievements-part">
                            <div class="joined-text">竞赛数</div>
                            <div class="joined-num">
                                {{ joinedCompetitionList.length }}
                                <span style="font-size: 14px;font-weight: normal;">场</span>
                            </div>
                        </div>
                        <div class="achievements-part">
                            <div class="joined-text">活动数</div>
                            <div class="joined-num">
                                {{ joinedActivityList.length }}
                                <span style="font-size: 14px;font-weight: normal;">个</span>
                            </div>
                        </div>
                        <div class="achievements-part">
                            <div class="joined-text">队伍数</div>
                            <div class="joined-num">
                                {{ joinedTeamList.length }}
                                <span style="font-size: 14px;font-weight: normal;">支</span>
                            </div>
                        </div>
                        <div class="achievements-part">
                            <div class="joined-text">帖子数</div>
                            <div class="joined-num">
                                {{ articleList.length }}
                                <span style="font-size: 14px;font-weight: normal;">条</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 帖子 -->
                <div class="user-article ">
                    <div class="u-a-title ">帖子</div>
                    <div class="article-list ">
                        <!-- none -->
                        <div class="none-tips" v-if="articleList.length == 0">(暂无帖子)</div>
                        <!-- item -->
                        <div class="article-item " v-for="item in articleList"
                            @click="$router.push({ path: 'article_details', query: { articleId: item.articleId } })">
                            <div class="article-title  ts02s">{{ item.articleTitle }}</div>
                            <div class="article-digest ">{{ item.articleDigest }}</div>
                            <div class="article-page-view">
                                <i class="el-icon-view"></i>
                                {{ item.articlePageView }}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Foot class="team-details-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import { mapActions } from 'vuex'
export default {
    data: () => ({
        teamLabel: ['Java', '编程', '硬件'], // 根据属性teamLabel前端计算得到
        user: {
            userHead: '',
            userName: '一二三四五六', // 8个英文字母，6个汉字
            intro: '吾剑之所指，吾心之所向123',
            sex: '0',
            status: '0', // 封禁显示
            identity: '0', // 身份
            collage: '信息与通信工程', // 学院
            specialty: '软件工程', // 专业
            clazz: '192', // 班级
            regDate: '2020-02-03', // 注册时间
        },
        joinedTeamList: [],
        articleList: [],
        joinedCompetitionList: [],
        joinedActivityList: [],
    }),
    methods: {
        ...mapActions('user', ['selectUserDetailVoById']),
        /* 初始化 */
        async init() {
            const { code, message, data } = await this.selectUserDetailVoById(this.$route.query.userId)
            if (code == 200) {
                this.user = { ...data.user }
                this.joinedTeamList = [...data.teamList]
                this.joinedCompetitionList = [...data.competitionList]
                this.joinedActivityList = [...data.activityList]
                this.articleList = [...data.articleList]
            }
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

            .user-head {
                position: absolute;
                width: 60px;
                border-radius: 50%;
            }

            .user-name {
                position: absolute;
                width: 200px;
                left: 75px;
                top: 5px;
                overflow: hidden;
                font-size: 18px;
                height: 25px;
                line-height: 25px;

                .user-name-content {
                    position: absolute;
                    left: 0;
                    width: 110px;
                }

                .user-status {
                    position: absolute;
                    right: 0;
                    overflow: hidden;
                    font-size: 12px;
                    color: red;
                }
            }


            .user-sex {
                position: absolute;
                width: 50px;
                font-size: 14px;
                left: 75px;
                top: 35px;
            }

            .user-identity {
                position: absolute;
                width: 50px;
                font-size: 14px;
                left: 95px;
                top: 35px;
                color: #909399;
            }

            .user-intro {
                position: absolute;
                width: 100%;
                top: 70px;

                .user-intro-text {
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

            .user-collage {
                position: absolute;
                width: 100%;
                top: 120px;
                font-size: 14px;
                color: #909399;
                height: 20px;
                line-height: 20px;

                .user-collage-content {
                    position: absolute;
                    right: 5px;
                }
            }

            .user-specialty-clazz {
                position: absolute;
                width: 100%;
                top: 145px;
                font-size: 14px;
                color: #909399;
                height: 20px;
                line-height: 20px;

                .user-specialty-clazz-content {
                    position: absolute;
                    right: 5px;
                }
            }

            .joined-team {
                position: absolute;
                width: 100%;
                top: 220px;
                height: 550px;

                .j-t-title {
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

                .joined-team-list {
                    position: absolute;
                    top: 40px;
                    width: 100%;

                    .joined-team-item {
                        position: relative;
                        width: 100%;
                        height: 60px;
                        margin-bottom: 10px;

                        .j-t-logo {
                            position: absolute;
                            top: 5px;
                            width: 50px;
                            border-radius: 50%;
                        }

                        .j-t-name {
                            display: flex;

                            .name-content {
                                position: absolute;
                                top: 5px;
                                color: #000000;
                                left: 65px;
                                font-weight: lighter;
                                font-size: 18px;
                                margin-right: 10px;
                            }

                            .j-t-mem-num {
                                position: absolute;
                                right: 5px;
                                top: 5px;
                                font-size: 12px;
                                color: #909399;
                            }
                        }

                        .j-t-intro {
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



            .user-achievements {
                position: relative;
                width: 98%;
                left: 1%;
                border: 1px solid #90939948;
                border-radius: 2px;
                height: 250px;
                overflow: hidden;
                margin-bottom: 20px;

                .achievements-title {
                    position: absolute;
                    left: 15px;
                    top: 10px;
                    font-size: 15px;
                    // font-weight: lighter;
                }

                .ach-flex {
                    position: absolute;
                    top: 50px;
                    width: 100%;
                    height: 200px;
                    display: flex;
                    flex-wrap: wrap;

                    .achievements-part {
                        position: relative;
                        width: 49.8%;
                        border-top: 1px solid #9093992d;


                        .joined-text {
                            position: absolute;
                            top: 25px;
                            width: 100%;
                            text-align: center;
                            font-size: 14px;
                        }

                        .joined-num {
                            position: absolute;
                            top: 50px;
                            width: 100%;
                            text-align: center;
                            font-size: 18px;
                            font-weight: bold;
                        }
                    }

                    .achievements-part:nth-child(1) {
                        border-right: 1px solid #9093992d;
                    }

                    .achievements-part:nth-child(3) {
                        border-right: 1px solid #9093992d;
                    }
                }


            }

            .user-article {
                position: absolute;
                width: 98%;
                left: 1%;
                border: 1px solid #90939948;
                border-radius: 2px;

                margin-bottom: 15px;
                overflow: hidden;

                .u-a-title {
                    position: relative;
                    left: 15px;
                    top: 10px;
                    font-size: 15px;
                    // font-weight: lighter;
                }

                .article-list {
                    position: relative;
                    top: 20px;
                    width: 100%;
                    height: auto;
                    overflow: scroll;
                    min-height: 80px;
                    max-height: 335px;
                    // max-height: 500px;
                    overflow: scroll;
                    padding-bottom: 20px;

                    .none-tips {
                        position: absolute;
                        font-size: 12px;
                        left: 15px;
                        top: 5px;
                        color: #909399;
                    }

                    .article-item {
                        position: relative;
                        width: 100%;
                        height: 50px;
                        overflow: hidden;
                        margin-top: 5px;
                        margin-bottom: 5px;

                        .article-title {
                            position: absolute;
                            font-size: 13px;
                            left: 15px;
                            color: #707070;
                            cursor: pointer;
                            width: 210px;
                            // 省略效果
                            overflow: hidden;
                            display: -webkit-box;
                            /*第几行裁剪*/
                            -webkit-line-clamp: 1;
                            -webkit-box-orient: vertical;
                        }

                        .article-title:hover {
                            color: #1e809e;

                        }

                        .article-digest {
                            position: absolute;
                            color: #a6a7aa;
                            top: 22px;
                            font-size: 12px;
                            left: 15px;
                            width: 250px;
                            // 省略效果
                            overflow: hidden;
                            display: -webkit-box;
                            /*第几行裁剪*/
                            -webkit-line-clamp: 1;
                            -webkit-box-orient: vertical;
                        }

                        .article-page-view {
                            position: absolute;
                            color: #909399;
                            font-size: 12px;
                            right: 10px;
                        }
                    }
                }
            }

        }

        .team-details-foot {
            position: absolute;
            top: 1100px;
            background-color: #00000000;
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
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0.247);
    background-color: rgba(158, 158, 158, 0.103);
}
</style>