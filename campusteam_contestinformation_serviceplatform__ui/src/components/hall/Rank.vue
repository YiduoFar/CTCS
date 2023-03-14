<template>
    <!-- 全校个人排名 -->
    <div class="model-rank ">
        <!-- title -->
        <div class="rank-title ">竞赛全校排名</div>
        <!-- tips -->
        <el-popover placement="top-start" title="" width="200" trigger="hover" :content="competitionRankTip"
            class="title-tips">
            <i slot="reference" class="el-icon-info"></i>
        </el-popover>
        <div class="rank-user-list ">
            <!-- item-个人 -->
            <div class="rank-user-item " v-for="item, index in userPointVoList">
                <!-- 名次 -->
                <div class="rank-user-item-num ">{{ index + 1 }}</div>
                <!-- 头像 -->
                <div class="rank-user-item-head">
                    <img :src="item.userHead" alt="" class="rank-user-head-img">
                </div>
                <!-- 昵称 -->
                <div class="rank-user-item-name">
                    {{ item.userName }}
                </div>
                <!-- 分数 -->
                <div class="rank-user-item-score">
                    {{ item.point }}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
    data: () => ({
        competitionRankTip: '根据参赛数*100得到的积分排序',
        userPointVoList: [],
    }),
    methods: {
        ...mapActions('count', ['getUserPointVoList']),
    },
    async created() {
        const { code, message, data } = await this.getUserPointVoList();
        if (code === 200) {
            this.userPointVoList = [...data]
        }
    },
}
</script>

<style lang="less" scoped>
.model-rank {
    width: 100%;
    height: 100%;

    .rank-title {
        position: absolute;
        top: 25px;
        left: 25px;
        font-size: 22px;
        font-style: italic;
        font-weight: bold;
    }

    .title-tips {
        position: absolute;
        top: 30px;
        left: 165px;
        font-size: 18px;
        cursor: pointer;
        color: #a59e9e;
    }

    .rank-user-list {
        position: absolute;
        top: 75px;
        width: 100%;
        height: 87%;
        overflow-y: scroll;
        overflow-x: hidden;

        .rank-user-item {
            position: relative;
            height: 30px;
            line-height: 30px;
            margin-bottom: 7px;

            .rank-user-item-num {
                position: absolute;
                width: 20px;
                left: 28px;
                font-size: 14px;
                color: #777888;
                text-align: center;
            }



            .rank-user-item-head {
                position: absolute;
                left: 70px;
                top: 3px;
                width: 24px;
                height: 24px;
                border-radius: 50%;
                overflow: hidden;

                .rank-user-head-img {
                    width: 24px;
                }
            }

            .rank-user-item-name {
                position: absolute;
                left: 110px;
                font-size: 14px;
            }

            .rank-user-item-score {
                position: absolute;
                right: 30px;
                font-size: 14px;
                color: #777888;
                font-weight: 600;
            }
        }

        .rank-user-item:nth-child(1)>.rank-user-item-num {
            color: #fc5533;
        }

        .rank-user-item:nth-child(2)>.rank-user-item-num {
            color: #fac525;
        }

        .rank-user-item:nth-child(3)>.rank-user-item-num {
            color: #cba598;
        }
    }
}
</style>