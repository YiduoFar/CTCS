<template>
    <!-- 招新 -->
    <div class="model-recruit ">
        <!-- title -->
        <el-divider content-position="left"><span class="recruit-title">队伍招募</span> </el-divider>
        <!-- 线 -->
        <div class="separator-op2"></div>
        <!-- 列表 -->
        <div class="recruit-team-list ">
            <div class="infinite-list-wrapper" style="overflow-x:hidden">
                <ul class="list" v-infinite-scroll="load" infinite-scroll-disabled="disabled"
                    style="margin: 0;padding: 0;;list-style:none;">
                    <div v-for="item in teamListShowView" class="list-item ts02s">
                        <img :src="item.teamLogo" alt="" class="team-logo">
                        <div class="team-name"
                            @click="$router.push({ path: '/team_details', query: { teamId: item.teamId } })">
                            <span class="t-n-sp ts02s">{{ item.teamName }}</span>
                        </div>
                        <div class="team-intro ">{{ item.teamIntro }}</div>
                        <div class="team-captain ">
                            <span class="t-c-sp ts02s"
                                @click="$router.push({ path: '/user_details', query: { userId: item.teamCaptionId } })">{{
                                    item.teamCaptionUserName
                                }}</span>
                        </div>
                        <div class=" team-status ">*{{ item.teamStatus == 0 ? '不招新' : '招新中' }}</div>
                        <div class=" team-joined-num ">{{ item.teamMemberNum }} / {{
                            item.teamMemberNumMax
                        }}
                        </div>
                        <div class=" team-join ts02s" @click="doJoinTeam(item)">立即加入
                        </div>
                    </div>
                </ul>
                <p v-if="loading" class="load-text ">加载中...</p>
                <p v-if="noMore" class="load-text ">没有更多了</p>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        count: 6,
        loading: false,
        teamList: [{
            teamLogo: '1',
            teamName: '你说的都队',
            teamIntro: '一起参加竞赛，一起参加活动，我们是最好的朋友，欢迎加入！',
            teamCaptain: 'Yiduofar',
            teamStatus: '1',
            teamJoinedNum: '5',
            teamJoinedNumLimit: '10'
        },
        {
            teamLogo: '2',
            teamName: '你说的都队',
            teamIntro: '一起参加竞赛，一起参加活动，我们是最好的朋友，欢迎加入！',
            teamCaptain: 'Yiduofar',
            teamStatus: '1',
            teamJoinedNum: '5',
            teamJoinedNumLimit: '10'
        }],
        teamListShowView: []
    }),
    computed: {
        noMore() {
            return this.count >= this.teamList.length
        },
        disabled() {
            return this.loading || this.noMore
        }
    },
    methods: {
        ...mapActions('user_team', ['selectAndJudgeOne']),
        ...mapActions('user_team', ['insertUserTeam']),
        load() {
            this.loading = true
            setTimeout(() => {
                this.count += 2
                this.teamListShowView = [...this.teamList.slice(0, this.count)]
                this.loading = false
            }, 2000)
        },
        ...mapActions('team', ['selectTeamRecruit']),
        /* 初始化 */
        async init() {
            const { code, message, data } = await this.selectTeamRecruit();
            if (code === 200) {
                this.teamList = [...data]
                this.teamListShowView = [...this.teamList.slice(0, this.count)]
            }
        },
        /* 申请加入队伍 */
        async doJoinTeam(item) {
            if (await this.beforeJoinTeam(item.teamId)) {
                this.$confirm(item.teamName, '申请加入队伍', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let userTeam = {
                        userId: localStorage.getItem('userId'),
                        teamId: item.teamId,
                        status: 0
                    }
                    const { code, message, data } = await this.insertUserTeam(userTeam)
                    if (code === 200) {
                        this.$message.success('已发送申请')
                    }
                }).catch(() => {

                })
            }
        },
        async beforeJoinTeam(teamId) {
            let judgeVo = {
                userId: localStorage.getItem('userId'),
                teamId: teamId
            }
            const { code, message, data } = await this.selectAndJudgeOne(judgeVo)
            return code === 200
        }
    },
    created() {
        this.init()
    },
    mounted() {
    }
}
</script>

<style lang="less" scoped>
.model-recruit {
    position: absolute;
    width: 74.5%;
    left: 0;
    height: 100%;
    background-color: #ffffffee;

    .recruit-title {
        font-size: 20px;
        font-weight: bold;
        color: #2b2b2ba6;
    }

    .recruit-team-list {
        width: 100%;
        height: 290px;
        position: absolute;
        top: 57px;
        overflow-y: scroll;
        overflow-x: hidden;

        .load-text {
            text-align: center;
            font-size: 12px;
            color: #a0a0a0;
        }

        .infinite-list-wrapper {
            height: 290px;

        }

        .list-item {
            width: 98%;
            height: 55px;
            border-top: 1px solid #e4e4e4;
            border-bottom: 1px solid #e4e4e4;
            list-style: none;
            position: relative;
            overflow-x: hidden;
            margin-top: 10px;
            left: 1%;

            .team-logo {
                position: absolute;
                top: 12px;
                left: 25px;
                width: 30px;

            }

            .team-name {
                position: absolute;
                left: 70px;
                font-size: 15px;
                top: 11px;
                width: 150px;
            }

            .t-n-sp:hover {
                color: #ca721f;
                cursor: pointer;
            }

            .team-intro {
                position: absolute;
                left: 70px;
                font-size: 12px;
                top: 33px;
                width: 350px;
                height: 15px;
                font-weight: lighter;

                // 省略效果
                overflow: hidden;
                display: -webkit-box;
                /*第几行裁剪*/
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;
            }

            .team-captain {
                position: absolute;
                left: 470px;
                font-size: 13px;
                top: 20px;
                width: 120px;
                text-align: center;
            }

            .t-c-sp:hover {
                color: #1fa2ca;
                cursor: pointer;
            }

            .team-status {
                position: absolute;
                left: 620px;
                font-size: 13px;
                top: 20px;
                width: 100px;
                text-align: center;
                color: #ff0f0fa6;
            }

            .team-joined-num {
                position: absolute;
                // top: 17px;
                top: 20px;
                left: 770px;
                font-size: 13px;
                color: #4e4e4ea4;
                width: 100px;
                text-align: center;

            }

            .team-join {
                position: absolute;
                // top: 17px;
                top: 20px;
                font-size: 13px;
                left: 910px;
                color: #00a8db;
                width: 100px;
                text-align: center;
                cursor: pointer;
            }

            .team-join:hover {
                color: #1bcaff;
            }
        }

        .list-item:hover {
            box-shadow: 1px 0px 10px #9e9e9ea6;
            // border: 1px solid #e4e4e4;

        }
    }

}
</style>