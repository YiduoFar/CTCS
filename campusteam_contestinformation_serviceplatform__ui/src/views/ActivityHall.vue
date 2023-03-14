<template>
    <!-- 活动大厅 -->
    <div class="activity-hall">
        <Nav></Nav>
        <div class="activity-hall-body">
            <img src="../assets/img/背景/竞赛大厅.png" alt="" class="bg">
            <div class="activity-model ">
                <!-- 最新活动 -->
                <div class="latest-activity-title">最新活动</div>
                <!-- 最新活动列表 -->
                <el-col v-for="item in latestActivity" class="latest-activity-item">
                    <el-card shadow="hover" class="latest-activity-item-card">
                        <div class="latest-activity-item-name"
                            @click="$router.push({ path: 'activity_details', query: { activityId: item.activity.activityId } })">
                            {{ item.activity.activityName }}</div>
                        <div class="latest-activity-item-intro">{{ item.activity.activityIntro }}</div>
                        <div class="latest-activity-item-date">{{
                            $moment(item.activity.activityPublishDate).format('YYYY.M.D Ah点') }}</div>
                        <div class="latest-activity-item-user-head "
                            @click="$router.push({ path: 'user_details', query: { userId: item.initiator.id } })">
                            <img class="latest-activity-item-user-head-img" :src="item.initiator.userHead" alt="">
                        </div>
                    </el-card>
                </el-col>
            </div>
            <!-- 所有活动 -->
            <div class="activity-view ">
                <!-- title -->
                <div class="activity-view-title">所有活动</div>
                <!-- tips -->
                <el-popover placement="top-start" width="120" trigger="hover" :content="activityListTip" class="title-tips">
                    <i slot="reference" class="el-icon-info"></i>
                </el-popover>
                <!-- 搜索 -->
                <div class="activity-view-search">
                    <el-input placeholder="搜索活动" v-model="queryVo.keyword" size="medium" style="width: 340px;"
                        @keyup.enter.native="search">
                        <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
                    </el-input>
                </div>
                <!-- 列表头 -->
                <div class="activity-view-list-head">
                    <div class="activity-list-head-item" v-for="item in activityHeadItem">
                        {{ item }}
                    </div>
                </div>
                <div class="activity-view-list-head-line separator-op2"></div>
                <!-- 分页列表 -->
                <div class="activity-view-list ">
                    <!-- 无数据 -->
                    <el-empty description="没有您想要的内容" :image-size="150" v-if="activityPage.length == 0"></el-empty>
                    <!-- 有数据 -->
                    <div class="activity-list-item ts02s" v-for="item in activityPage">
                        <div class="activity-list-item-i "><span class="nth1 ts02s"
                                @click="$router.push({ path: 'activity_details', query: { activityId: item.activity.activityId } })">{{
                                    item.activity.activityName }}</span>
                        </div>
                        <div class="activity-list-item-i ">{{ activityType[item.activity.activityType] }}
                        </div>
                        <div class="activity-list-item-i " v-if="item.activity.activityPublishDate">{{
                            $moment(item.activity.activityPublishDate).format('YYYY.MM.DD') }}</div>
                        <div class="activity-list-item-i " v-if="item.activity.activityStatus == 0" style="color:grey;">{{
                            activityStatus[0] }}</div>
                        <div class="activity-list-item-i " v-if="item.activity.activityStatus == 1" style="color:red;">{{
                            activityStatus[1] }}</div>
                        <div class="activity-list-item-i ">{{
                            activityAudience[item.activity.activityAudience] }}</div>
                        <div class="activity-list-item-i ts02s"
                            @click="$router.push({ path: 'user_details', query: { userId: item.initiator.id } })">
                            <img :src="item.initiator.userHead" alt="" class="initiator-head">
                            {{ item.initiator.userName }}
                        </div>
                        <div class="activity-list-item-i ">{{ item.activity.activityJoinedNum }}</div>
                        <div class="activity-list-item-i"><span style="cursor: pointer;"
                                @click="$router.push({ path: 'activity_details', query: { activityId: item.activity.activityId } })">查看详情</span>
                        </div>
                        <!-- <div class="activity-list-item-i"><span style="cursor: pointer;">立即报名</span></div> -->
                    </div>
                </div>
                <!-- 分页 -->
                <div class="activity-list-page ">
                    <el-pagination layout="prev, pager, next" :total="totalPage" class="page-el-pagination"
                        @current-change="currentChange">
                    </el-pagination>
                </div>
            </div>
            <Foot class="activity-hall-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import { mapActions } from 'vuex';
export default {
    data: () => ({
        activityHeadItem: ['活动名称', '活动类型', '时间', '状态', '受众', '发起人', '已报名', '操作'],
        /**
         * 分页Vo
         */
        queryVo: {
            current: 1,
            size: 13,
            keyword: ''
        },
        /**
         * 总页数
         */
        totalPage: 0,
        /**
         * 最新活动
         */
        latestActivity: [],
        activityListTip: '根据报名人数排序',
        /**
         * 所有活动
         */
        activityPage: [],
        activityType: ['团建', '聚会', '运动', '社团', '户外', '游戏'],
        activityStatus: ['已结束', '进行中'],
        activityAudience: ['面向全校', '面向学院', '面向专业', '面向个人'],
    }),
    methods: {
        ...mapActions('activity', ['selectActivityByKeyword']),
        ...mapActions('activity', ['selectLatestActivity']),
        async init() {
            this.search()
            this.getLatestActivity()
        },
        /**
         * 搜索
         */
        async search() {
            const { code, message, data } = await this.selectActivityByKeyword(this.queryVo)
            if (code === 200) {
                this.activityPage = [...data.page]
                // 计算总页数
                let size = this.queryVo.size;
                let total = data.total
                this.totalPage = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            } else {
                this.activityPage = []
            }
        },
        /**
         * 最近的竞赛（5个）
         */
        async getLatestActivity() {
            const { code, message, data } = await this.selectLatestActivity()
            if (code === 200) {
                this.latestActivity = [...data]
            }
        },
        currentChange(e) {
            this.queryVo.current = e
            this.search()
        }
    },
    created() {
        this.init()
    },
    watch: {
        'queryVo.keyword'() {
            this.search()
        }
    },
    components: {
        Nav, Foot
    }
}
</script>

<style lang="less" scoped>
.activity-hall {
    width: 100%;
    height: auto;

    .activity-hall-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;
        background-color: #f7f7f7;

        .bg {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            filter: blur(0px) brightness(100%);
            z-index: -1;
            transform: scale(1.55);
            //   transition: filter 1.5s;
        }

        .activity-model {
            position: absolute;
            width: 74%;
            height: 215px;
            overflow: hidden;
            top: 80px;
            left: 13%;
            background-color: #fff;
            box-shadow: 0px 5px 5px #e2e2e2a6;

            .latest-activity-title {
                position: absolute;
                top: 10px;
                left: 25px;
                font-size: 20px;
                font-weight: bold;
                color: #2b2b2ba6;
                // font-style: italic;
            }

            .latest-activity-item {
                position: relative;
                top: 55px;
                left: 25px;
                margin-right: 10px;
                width: 265px;
                cursor: pointer;



                .latest-activity-item-card {
                    height: 130px;

                    .latest-activity-item-name {
                        position: absolute;
                        top: 10px;
                        left: 15px;
                        font-size: 16px;
                        font-weight: bolder;
                        color: #0f0f0fa6;
                        width: 240px;
                        height: 80px;
                        z-index: 1;
                    }

                    .latest-activity-item-intro {
                        position: absolute;
                        top: 45px;
                        left: 15px;
                        font-size: 12px;
                        color: #4e4e4e;
                        width: 240px;


                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;
                    }

                    .latest-activity-item-date {
                        position: absolute;
                        top: 100px;
                        left: 15px;
                        font-size: 12px;
                        color: #4e4e4ea4;
                        font-weight: bold;
                    }

                    .latest-activity-item-user-head {
                        position: absolute;
                        top: 95px;
                        right: 15px;
                        border-radius: 50%;
                        overflow: hidden;
                        width: 25px;
                        height: 25px;

                        .latest-activity-item-user-head-img {
                            width: 25px;
                        }
                    }
                }
            }


        }

        .activity-view {
            position: absolute;
            width: 74%;
            height: 820px;
            overflow: hidden;
            top: 310px;
            left: 13%;
            background-color: #fff;

            .activity-view-title {
                position: absolute;
                top: 10px;
                left: 25px;
                font-size: 20px;
                font-weight: bold;
                color: #2b2b2ba6;
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

            .activity-view-search {
                position: absolute;
                top: 10px;
                right: 20px;
            }

            .activity-view-list-head {
                position: absolute;
                top: 60px;
                width: 100%;
                height: 25px;
                border-top: 1px solid #f7f7f7;
                display: flex;
                color: #777888;
                font-size: 14px;
                font-weight: bolder;

                .activity-list-head-item:nth-child(1) {
                    position: absolute;
                    left: 35px;
                }

                .activity-list-head-item:nth-child(2) {
                    position: absolute;
                    left: 350px;
                }

                .activity-list-head-item:nth-child(3) {
                    position: absolute;
                    left: 530px;
                }

                .activity-list-head-item:nth-child(4) {
                    position: absolute;
                    left: 680px;
                }

                .activity-list-head-item:nth-child(5) {
                    position: absolute;
                    left: 830px;
                }

                .activity-list-head-item:nth-child(6) {
                    position: absolute;
                    left: 990px;
                }

                .activity-list-head-item:nth-child(7) {
                    position: absolute;
                    left: 1150px;
                }

                .activity-list-head-item:nth-child(8) {
                    position: absolute;
                    left: 1320px;
                }
            }

            .activity-view-list-head-line {
                position: absolute;
                width: 100%;
                top: 85px;
            }

            .activity-view-list {
                position: absolute;
                width: 100%;
                height: 81%;
                top: 95px;
                overflow: hidden;


                .activity-list-item {
                    display: flex;
                    font-size: 13px;
                    color: rgb(150, 150, 150);
                    height: 50px;
                    line-height: 50px;
                    border-top: 1px solid #e0e0e091;
                    text-align: center;

                    .activity-list-item-i {
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;

                        .initiator-head {
                            width: 25px;
                            position: relative;
                            top: 6px;
                            margin-right: 2px;
                            border-radius: 50%;
                        }
                    }

                    .activity-list-item-i:nth-child(1) {
                        position: absolute;
                        left: 35px;
                        color: #000000a6;
                        font-weight: bolder;
                        width: 250px;
                        // border: 1px solid red;
                        text-align: left;


                        .nth1 {
                            cursor: pointer;
                        }

                        .nth1:hover {
                            color: #40a9ff;
                        }
                    }

                    .activity-list-item-i:nth-child(2) {
                        position: absolute;
                        left: 350px;
                        width: 50px;
                    }

                    .activity-list-item-i:nth-child(3) {
                        position: absolute;
                        left: 485px;
                        width: 120px;
                    }

                    .activity-list-item-i:nth-child(4) {
                        position: absolute;
                        left: 677px;

                    }

                    .activity-list-item-i:nth-child(5) {
                        position: absolute;
                        left: 808px;
                        width: 80px;
                    }

                    .activity-list-item-i:nth-child(6) {
                        position: absolute;
                        left: 920px;
                        width: 180px;
                        cursor: pointer;
                    }

                    .activity-list-item-i:nth-child(6):hover {
                        color: #2987a3;
                    }

                    .activity-list-item-i:nth-child(7) {
                        position: absolute;
                        left: 1135px;
                        width: 70px;
                        // border: 1px solid red;
                    }

                    .activity-list-item-i:nth-child(8) {
                        position: absolute;
                        left: 1308px;
                        color: #277ccc;
                    }

                    // .activity-list-item-i:nth-child(9) {
                    //     position: absolute;
                    //     left: 1340px;
                    //     color: #277ccc;
                    // }


                }

                .activity-list-item:hover {
                    background-color: #f5f6f7b3;
                }

                .activity-list-item:nth-child(1) {
                    border: none;
                }
            }

            .activity-list-page {
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

        .activity-hall-foot {
            position: absolute;
            top: 1150px;
            background-color: #00000000;
        }
    }
}
</style>