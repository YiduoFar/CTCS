<template>
    <!-- 竞赛大厅 -->
    <div class="competition-hall">
        <Nav></Nav>
        <div class="competition-hall-body">
            <!-- 背景 -->
            <img src="../assets/img/背景/竞赛大厅.png" alt="" class="bg">

            <div class="competition-model">
                <!-- 左边部分 -->
                <div class="model-left-part">
                    <!-- 轮播图 -->
                    <Slideshow2 class="model-slideshow"></Slideshow2>
                    <!-- 往届竞赛回顾.png -->
                    <div class="model-title">
                        <img src="../assets/img/背景/往届竞赛回顾.png" alt="" class="model-title-img">
                    </div>
                    <!-- 往届竞赛列表 -->
                    <OldCompetitionList class="model-old-competition-list"></OldCompetitionList>
                </div>
                <!-- 右边部分 -->
                <div class="model-right-part">
                    <!-- 全校个人排名 -->
                    <Rank class="model-rank"></Rank>
                </div>
            </div>
            <!-- 竞赛一览 -->
            <div class="competition-view">
                <div class="competition-view-title">所有竞赛</div>
                <!-- tips -->
                <el-popover placement="top-start" title="Tips" width="200" trigger="hover" :content="competitionListTip"
                    class="title-tips">
                    <i slot="reference" class="el-icon-info"></i>
                </el-popover>
                <!-- 搜索 -->
                <div class="competition-view-search">
                    <el-input placeholder="搜索竞赛" v-model="queryVo.keyword" size="medium" style="width: 340px;"
                        @keyup.enter.native="search">
                        <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
                    </el-input>
                </div>
                <!-- 列表头 -->
                <div class="competition-view-list-head">
                    <div class="competition-list-head-item" v-for="item in competitionHeadItem">
                        {{ item }}
                    </div>
                </div>
                <div class="competition-view-list-head-line separator-op2"></div>
                <!-- 竞赛列表 -->
                <div class="competiton-view-list ">
                    <!-- 无数据 -->
                    <el-empty description="没有您想要的内容" :image-size="150" v-if="competitionPage.length == 0"></el-empty>
                    <!-- 竞赛item -->
                    <div class="competition-list-item ts02s" v-for="item in competitionPage">
                        <!-- 竞赛item字段 -->
                        <div class="competition-list-item-i ">
                            <!-- 竞赛名称 -->
                            <span class="nth1 ts02s"
                                @click="$router.push({ path: '/competition_details', query: { competitionId: item.competitionId } })">
                                {{ item.competitionName }}
                            </span>
                        </div>
                        <!-- 承办方 -->
                        <div class="competition-list-item-i ">{{ item.competitionUndertaker }}</div>
                        <!-- 发布时间 -->
                        <div class="competition-list-item-i ">{{ String(item.competitionPublishDate).substring(0, 10) }}
                        </div>
                        <!-- 竞赛状态 已结束-->
                        <div class="competition-list-item-i " v-if="item.competitionStatus == 0">{{ competitionStatus[0] }}
                        </div>
                        <!-- 竞赛状态 进行中 -->
                        <div class="competition-list-item-i " v-if="item.competitionStatus == 1" style="color: red;">*{{
                            competitionStatus[1] }}</div>
                        <!-- 受众 -->
                        <div class="competition-list-item-i">{{ competitionAudience[item.competitionAudience] }}</div>
                        <!-- 类型 -->
                        <div class="competition-list-item-i ">{{ competitionType[item.competitionType] }}</div>
                        <!-- 已报名数量 -->
                        <div class="competition-list-item-i">{{ item.competitionJoinedNum }}</div>
                        <!-- 查看详情 -->
                        <div class="competition-list-item-i"><span style="cursor: pointer;"
                                @click="$router.push({ path: '/competition_details', query: { competitionId: item.competitionId } })">查看详情</span>
                        </div>
                    </div>
                </div>
                <!-- 分页组件 -->
                <div class="competition-list-page ">
                    <el-pagination layout="prev, pager, next" :total="totalPage" class="page-el-pagination"
                        @current-change="currentChange">
                    </el-pagination>
                </div>
            </div>
            <Foot class="competition-hall-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import Slideshow2 from '../components/hall/Slideshow2.vue'
import OldCompetitionList from '@/components/hall/OldCompetitionList.vue';
import Rank from '@/components/hall/Rank.vue';
import { mapActions } from 'vuex';
export default {
    data: () => ({
        /**
         * 查询Vo
         * size: 8 （一页8数据）
         * current: 1 （默认当前页1）
         */
        queryVo: {
            current: 1,
            size: 8,
            keyword: ''
        },
        /**
         * 页数
         */
        totalPage: 0,
        /**
         * 竞赛分页列表
         */
        competitionPage: [],
        /**
         * 竞赛表表头
         */
        competitionHeadItem: ['竞赛全称', '承办方', '时间', '状态', '受众', '竞赛类型', '已报名', '操作'],
        /**
         * 排序规则提示tip
         */
        competitionListTip: '默认根据竞赛的发布时间，从最近开始往下排序',
        /**
         * 竞赛类型
         */
        competitionType: ['工科竞赛', '文科竞赛'],
        /**
         * 竞赛受众
         */
        competitionAudience: ['面向全校', '面向学院', '面向专业', '面向个人'],
        /**
         * 竞赛状态
         */
        competitionStatus: ['已结束', '进行中'],
    }),
    created() {
        this.init()
    },
    methods: {
        /**
         * 得到竞赛全校排行
         */
        ...mapActions('competition', ['getUserPointVoList']),
        /**
         * 根据关键字搜索竞赛
         */
        ...mapActions('competition', ['selectCompetitionByKeyWord']),
        /**
         * 初始化页面数据
         */
        init() {
            this.search()
        },
        /**
         * 搜索竞赛
         */
        async search() {
            // 根据关键字搜索竞赛
            const { code, message, data } = await this.selectCompetitionByKeyWord(this.queryVo)
            if (code === 200) {
                // 解构赋值
                this.competitionPage = [...data.page]
                // 计算总页数
                let size = this.queryVo.size
                let total = data.total
                this.totalPage = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            } else {
                this.competitionPage = []
            }
        },
        /**
         * 页数变化的钩子
         * @param {*} e 
         */
        currentChange(e) {
            // 赋值当前页
            this.queryVo.current = e
            // 查询当前页
            this.search()
        }
    },
    watch: {
        /**
         * 监听搜索框关键字变化，进行搜索
         */
        'queryVo.keyword'() {
            this.search()
        }
    },
    components: {
        Nav,
        Foot,
        Slideshow2,
        OldCompetitionList,
        Rank,
    },
}
</script>

<style lang="less" scoped>
.competition-hall {
    width: 100%;
    height: auto;

    .competition-hall-body {
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
            transform: scale(2.7);
            //   transition: filter 1.5s;
        }

        .competition-model {
            position: absolute;
            width: 74%;
            height: 800px;
            overflow: hidden;
            top: 80px;
            left: 13%;

            .model-left-part {
                position: absolute;
                width: 75%;
                height: 100%;
                left: 0;
                background-color: #fff;

                .model-slideshow {
                    position: absolute;
                    top: 25px;
                    left: 25px;
                }

                .model-title {
                    position: absolute;
                    left: 25px;
                    top: 305px;
                    overflow: hidden;
                    width: 1010px;
                    height: 70px;
                    border-radius: 10px;

                    .model-title-img {
                        position: absolute;
                        top: -22px;
                        width: 1040px;
                    }
                }

                .model-old-competition-list {
                    position: absolute;
                    top: 400px;
                    left: 25px;
                }
            }

            .model-right-part {
                position: absolute;
                width: 25%;
                height: 100%;
                right: 0;
                background-color: #fafbfbcc;
            }
        }

        .competition-view {
            position: absolute;
            width: 74%;
            height: 800px;
            overflow: hidden;
            top: 900px;
            left: 13%;
            background-color: #fff;

            .competition-view-title {
                position: absolute;
                top: 10px;
                left: 25px;
                font-size: 20px;
                font-weight: bold;
                color: #000000a6;
            }

            .title-tips {
                position: absolute;
                top: 15px;
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

            .competition-view-search {
                position: absolute;
                top: 10px;
                right: 20px;

            }

            .competition-view-list-head {
                position: absolute;
                top: 60px;
                width: 100%;
                height: 25px;
                border-top: 1px solid #f7f7f7;
                display: flex;
                color: #777888;
                font-size: 14px;
                font-weight: bolder;

                .competition-list-head-item:nth-child(1) {
                    position: absolute;
                    left: 35px;
                }

                .competition-list-head-item:nth-child(2) {
                    position: absolute;
                    left: 420px;
                }

                .competition-list-head-item:nth-child(3) {
                    position: absolute;
                    left: 585px;
                }

                .competition-list-head-item:nth-child(4) {
                    position: absolute;
                    left: 740px;
                }

                .competition-list-head-item:nth-child(5) {
                    position: absolute;
                    left: 890px;
                }

                .competition-list-head-item:nth-child(6) {
                    position: absolute;
                    left: 1030px;
                }

                .competition-list-head-item:nth-child(7) {
                    position: absolute;
                    left: 1180px;
                }

                .competition-list-head-item:nth-child(8) {
                    position: absolute;
                    left: 1320px;
                }
            }

            .competition-view-list-head-line {
                position: absolute;
                width: 100%;
                top: 85px;
            }

            .competiton-view-list {
                position: absolute;
                width: 100%;
                height: 83%;
                top: 95px;
                overflow: hidden;

                .competition-list-item {
                    display: flex;
                    font-size: 13px;
                    color: rgb(150, 150, 150);
                    height: 73px;
                    line-height: 73px;
                    border-top: 1px solid #e0e0e091;
                    text-align: center;

                    .competition-list-item-i {
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;
                    }

                    .competition-list-item-i:nth-child(1) {
                        position: absolute;
                        left: 35px;
                        color: #000000a6;
                        font-weight: bolder;
                        width: 325px;
                        // border: 1px solid red;
                        text-align: left;
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;

                        .nth1 {
                            cursor: pointer;
                        }

                        .nth1:hover {
                            color: #4499df;
                        }
                    }

                    .competition-list-item-i:nth-child(2) {
                        position: absolute;
                        left: 368px;
                        width: 150px;
                    }

                    .competition-list-item-i:nth-child(3) {
                        position: absolute;
                        left: 540px;
                        width: 120px;
                    }

                    .competition-list-item-i:nth-child(4) {
                        position: absolute;
                        left: 737px;
                    }

                    .competition-list-item-i:nth-child(5) {
                        position: absolute;
                        left: 868px;
                        width: 80px;
                    }

                    .competition-list-item-i:nth-child(6) {
                        position: absolute;
                        left: 970px;
                        width: 180px;
                    }

                    .competition-list-item-i:nth-child(7) {
                        position: absolute;
                        left: 1165px;
                        width: 70px;
                        // border: 1px solid red;
                    }

                    .competition-list-item-i:nth-child(8) {
                        position: absolute;
                        left: 1310px;
                        color: #277ccc;
                    }

                }

                .competition-list-item:hover {
                    background-color: #f5f6f7b3;
                }

                .competition-list-item:nth-child(1) {
                    border: none;
                }
            }

            .competition-list-page {
                position: absolute;
                bottom: 0;
                left: 0;
                height: 40px;
                width: 100%;
                display: flex;
                justify-content: center;

                .page-el-pagination {
                    position: absolute;
                    top: 3px;
                }
            }
        }

        .competition-hall-foot {
            position: absolute;
            top: 1720px;
            background-color: #00000000;
        }
    }


}
</style>
