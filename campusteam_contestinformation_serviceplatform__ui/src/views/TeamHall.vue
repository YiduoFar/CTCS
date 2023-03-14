<template>
    <div class="team-hall">
        <Nav></Nav>
        <div class="team-hall-body">
            <img src="../assets/img/背景/竞赛大厅.png" alt="" class="bg">
            <div class="team-model ">
                <TeamRecruit></TeamRecruit>
                <TeamUserView></TeamUserView>
            </div>
            <!-- 所有队伍 -->
            <div class="team-view ">
                <!-- title -->
                <div class="team-view-title ">所有队伍</div>
                <!-- 邀请码加入 -->
                <div class="fast-join ">
                    <el-input size="mini" style="width: 150px;" v-model="teamInviteCode" placeholder="邀请码"></el-input>
                    <div class="fast-join-btn ts02s" @click="doFastJoin()">快速加入</div>
                </div>
                <!-- tips -->
                <el-popover placement="top-start" width="150" trigger="hover" :content="teamListTip" class="title-tips">
                    <i slot="reference" class="el-icon-info"></i>
                </el-popover>
                <!-- 搜索 -->
                <div class="team-view-search">
                    <el-input placeholder="搜索队伍" v-model="queryVo.keyword" size="small" style="width:300px;"
                        @keyup.enter.native="search()">
                        <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
                    </el-input>
                </div>
                <!-- 列表头 -->
                <div class="team-view-list-head">
                    <div class="team-list-head-item" v-for="item in teamHeadItem">
                        {{ item }}
                    </div>
                </div>
                <div class="team-view-list-head-line separator-op2"></div>
                <!-- 分页列表 -->
                <div class="team-view-list ">
                    <div class="team-list-item ts02s" v-for="item in teamPage">
                        <img :src="item.team.teamLogo" alt="" class="team-logo-img">
                        <div class="team-list-item-i "><span class="nth1 ts02s"
                                @click="$router.push({ path: '/team_details', query: { teamId: item.team.teamId } })">{{
                                    item.team.teamName }}</span>
                        </div>
                        <!-- 队长头像 -->
                        <img :src="item.caption.userHead" alt="" class="team-captain-head">
                        <div class="team-list-item-i "
                            @click="$router.push({ path: '/user_details', query: { teamId: item.caption.id } })">{{
                                item.caption.userName }}</div>
                        <div class="team-list-item-i ">
                            <span v-if="item.team.teamStatus == 0" style="color: #6d6d6da6;">不招新</span>
                            <span v-if="item.team.teamStatus == 1" style="color: #ff0000a6;">*招新中</span>
                            <!-- {{ item.teamStatus == 1 ? '不招新' : '*招新中' }} -->
                        </div>
                        <div class="team-list-item-i ">{{ item.team.teamMemberNum }} / {{
                            item.team.teamMemberNumMax
                        }}</div>
                        <div class="team-list-item-i "><span style="cursor: pointer;"
                                @click="$router.push({ path: '/team_details', query: { teamId: item.team.teamId } })">查看详情</span>
                        </div>
                        <!-- <div class="team-list-item-i "><span style="cursor: pointer;">立即加入</span></div> -->
                    </div>
                </div>
                <!-- 分页 -->
                <div class="team-list-page ">
                    <el-pagination layout="prev, pager, next" :total="totalPage" class="page-el-pagination"
                        @current-change="currentChange">
                    </el-pagination>
                </div>
            </div>

            <!-- 队伍星级 -->
            <TeamStars></TeamStars>
            <Foot class="team-hall-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import TeamRecruit from '../components/hall/TeamRecruit.vue'
import TeamStars from '@/components/hall/TeamStars.vue';
import TeamUserView from '@/components/hall/TeamUserView.vue';
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
export default {
    data: () => ({
        /* 表头 */
        teamHeadItem: ['队伍名称', '队长', '状态', '人员', '操作'],
        /* 总页数 */
        totalPage: 0,
        /* 页数据 */
        teamPage: [{
            team:{
                teamLogo: '2',
                teamName: 'BAT最大的粉丝团',
                teamCaptain: 'Yiduofar',
                teamStatus: '1',
                teamJoinedNum: '5',
                teamJoinedNumLimit: '10'
            },
            caption:{
                userHead:''
            }
        }],
        /* 分页查询Vo */
        queryVo: {
            current: 1,
            size: 13,
            keyword: ''
        },
        teamListTip: '根据创建时间倒序排序',
        teamInviteCode: '',
    }),
    computed: {
    },
    components: {
        Nav,
        Foot,
        TeamRecruit,
        TeamStars,
        TeamUserView
    },
    methods: {
        ...mapActions('team', ['selectTeamByKeyword']),
        init() {
            this.search()
        },
        async search() {
            const { code, message, data } = await this.selectTeamByKeyword(this.queryVo);
            if (code === 200) {
                this.teamPage = [...data.page];
                // 计算总页数
                let size = this.queryVo.size;
                let total = data.total
                this.totalPage = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            } else {
                this.teamPage = [];
            }
        },
        currentChange(e) {
            this.queryVo.current = e;
            this.search();
        },
        ...mapActions('user_team', ['fastJoinTeam']),
        /* 邀请码入队 */
        async doFastJoin() {
            if (!this.teamInviteCode){
                this.$message.error('请输入邀请码');
                return;
            }
            var formdata = new window.FormData()
            formdata.append('userId', localStorage.getItem('userId'))
            formdata.append('teamInviteCode', this.teamInviteCode)
            const { code, message, data } = await this.fastJoinTeam(formdata)
            if (code === 200) {
                ElementUI.Message.success('成功加入队伍：' + data.teamName)
                this.teamInviteCode = ''
                setTimeout(()=>{
                    location.reload()
                },1000)
            }
        }
    },
    created() {
        this.init();
    },
    watch: {
        /**
         * 监听搜索框关键字变化，进行搜索
         */
        'queryVo.keyword'() {
            this.search()
        }
    },

}
</script>

<style lang="less" scoped>
.team-hall {
    width: 100%;
    height: auto;

    .team-hall-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;
        // background-color: #f7f7f7;

        .bg {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            filter: blur(0px) brightness(100%);
            z-index: -1;
            transform: scale(1.8);
            //   transition: filter 1.5s;
        }

        .team-model {
            position: absolute;
            width: 74%;
            height: 360px;
            overflow: hidden;
            top: 80px;
            left: 13%;

            // background-color: #fff;
            // box-shadow: 0px 5px 5px #e2e2e2a6;



        }

        .team-view {
            position: absolute;
            width: 55.1%;
            height: 820px;
            overflow: hidden;
            top: 455px;
            left: 13%;
            background-color: #fff;

            .team-view-title {
                position: absolute;
                top: 10px;
                left: 25px;
                font-size: 20px;
                font-weight: bold;
                color: #2b2b2ba6;
            }

            .fast-join {
                position: absolute;
                top: 11px;
                left: 140px;
                font-size: 14px;
                width: 215px;
                height: 25px;

                .fast-join-btn {
                    position: absolute;
                    top: 0;
                    right: 0;
                    height: 28px;
                    line-height: 28px;
                    width: 60px;
                    text-align: center;
                    font-size: 12px;
                    background-color: #38c266;
                    color: #fff;
                    cursor: pointer;
                    border-radius: 5px;
                }

                .fast-join-btn:hover {
                    background-color: #219e4b;
                }
            }

            .title-tips {
                position: absolute;
                top: 14px;
                left: 110px;
                width: 20px;
                height: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;
                font-size: 18px;
                color: #a59e9e;
            }

            .team-view-search {
                position: absolute;
                top: 10px;
                right: 20px;
            }

            .team-view-list-head {
                position: absolute;
                top: 60px;
                width: 100%;
                height: 25px;
                border-top: 1px solid #f7f7f7;
                display: flex;
                color: #777888;
                font-size: 14px;
                font-weight: bolder;

                .team-list-head-item:nth-child(1) {
                    position: absolute;
                    left: 80px;
                }

                .team-list-head-item:nth-child(2) {
                    position: absolute;
                    left: 420px;
                }

                .team-list-head-item:nth-child(3) {
                    position: absolute;
                    left: 600px;
                }

                .team-list-head-item:nth-child(4) {
                    position: absolute;
                    left: 770px;
                }

                .team-list-head-item:nth-child(5) {
                    position: absolute;
                    left: 947px;
                }
            }

            .team-view-list-head-line {
                position: absolute;
                width: 100%;
                top: 85px;
            }

            .team-view-list {
                position: absolute;
                width: 100%;
                height: 81%;
                top: 95px;
                overflow: hidden;



                .team-list-item {
                    display: flex;
                    font-size: 13px;
                    color: rgb(150, 150, 150);
                    height: 50px;
                    line-height: 50px;
                    border-top: 1px solid #e0e0e091;
                    text-align: center;

                    .team-list-item-i {
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;
                    }

                    .team-logo-img {
                        position: relative;
                        left: 40px;
                        width: 25px;
                        height: 25px;
                        top: 12px;
                    }

                    .team-list-item-i:nth-child(2) {
                        position: absolute;
                        left: 80px;
                        color: #000000a6;
                        font-weight: bolder;
                        width: 250px;
                        // border: 1px solid red;
                        text-align: left;


                        .nth1 {
                            cursor: pointer;
                        }

                        .nth1:hover {
                            color: #ca721f;

                        }
                    }

                    .team-captain-head {
                        position: relative;
                        left: 380px;
                        width: 25px;
                        height: 25px;
                        border-radius: 50%;
                        top: 12px;
                    }


                    .team-list-item-i:nth-child(4) {
                        position: absolute;
                        left: 440px;
                        width: 80px;
                        text-align: left;
                        cursor: pointer;
                    }

                    .team-list-item-i:nth-child(5) {
                        position: absolute;
                        left: 558px;
                        width: 120px;
                    }

                    .team-list-item-i:nth-child(6) {
                        position: absolute;
                        left: 768px;

                    }

                    .team-list-item-i:nth-child(7) {
                        position: absolute;
                        left: 925px;
                        width: 70px;
                        color: #277ccc;

                    }

                    // .team-list-item-i:nth-child(8) {
                    //     position: absolute;
                    //     left: 960px;
                    //     width: 70px;
                    //     color: #277ccc;
                    // }

                }

                .team-list-item:hover {
                    background-color: #f5f6f7b3;
                }

                .team-list-item:nth-child(1) {
                    border: none;
                }
            }

            .team-list-page {
                position: absolute;
                bottom: 0;
                left: 0;
                height: 60px;
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;

                .page-el-pagination {
                    position: absolute;
                    top: 10px;
                }
            }
        }



        .team-hall-foot {
            position: absolute;
            top: 1285px;
            background-color: #00000000;
        }

    }
}
</style>