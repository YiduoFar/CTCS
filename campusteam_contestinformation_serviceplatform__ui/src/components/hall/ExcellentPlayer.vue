<template>
    <div class="e-player ">
        <!-- title -->
        <div class="e-player-title">优秀个人</div>
        <!-- 前三 -->
        <div class="e-player-3">
            <div class="e-player-3-head ts02s  top-1 " :title="'参赛' + excellentPlayerVoList[0].joinedNum + '次'">
                <img :src="excellentPlayerVoList[0].userHead" alt="" class="e-player-3-head-img">
            </div>
            <div class="e-player-3-name">{{ excellentPlayerVoList[0].userName }}</div>
        </div>
        <div class="e-player-3">
            <div class="e-player-3-head ts02s  top-2" :title="'参赛' + excellentPlayerVoList[1].joinedNum + '次'">
                <img :src="excellentPlayerVoList[1].userHead" alt="" class="e-player-3-head-img">
            </div>
            <div class="e-player-3-name ">{{ excellentPlayerVoList[1].userName }}</div>
        </div>
        <div class="e-player-3">
            <div class="e-player-3-head ts02s  top-3" :title="'参赛' + excellentPlayerVoList[2].joinedNum + '次'">
                <img :src="excellentPlayerVoList[2].userHead" alt="" class="e-player-3-head-img">
            </div>
            <div class="e-player-3-name ">{{ excellentPlayerVoList[2].userName }}</div>
        </div>
        <!-- 4开始 -->
        <div class="e-player-after  ts05s">
            <div class="e-player-after-item " v-for="item, index in excellentPlayerVoListSplitter">
                <div class="e-player-after-item-inner">
                    <!-- 名次 -->
                    <div class="e-player-after-item-inner-number ">
                        {{ index + 4 }}
                    </div>
                    <!-- 头像 -->
                    <div class="e-player-after-item-inner-head ">
                        <img :src="item.userHead" alt="" class="e-player-after-item-inner-head-img">
                    </div>
                    <!-- 昵称 -->
                    <div class="e-player-after-item-inner-username ">
                        {{ item.userName }}
                    </div>
                    <!-- 参赛数 -->
                    <div class="e-player-after-item-inner-contests-num ">
                        已参加竞赛{{ item.joinedNum }}次
                    </div>
                </div>
            </div>
            <div class="tips">根据参赛数量排序</div>
        </div>
        <!-- foot -->
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        excellentPlayerVoList: [{
            userHead:'',
            userName:'',
            joinedNum: 0,
        },{
            userHead:'',
            userName:'',
            joinedNum: 0,
        },{
            userHead:'',
            userName:'',
            joinedNum: 0,
        }],
        excellentPlayerVoListSplitter: [],
    }),
    methods: {
        ...mapActions('count', ['getExcellentPlayerVoList'])
    },
    async created() {
        const { code, message, data } = await this.getExcellentPlayerVoList();
        this.excellentPlayerVoList = [...data];
        this.excellentPlayerVoListSplitter = this.excellentPlayerVoList.slice(3, Math.min(this.excellentPlayerVoList.length, 11))
    }
}
</script>

<style lang="less" scoped>
.e-player {
    position: absolute;
    width: 350px;
    height: 530px;
    // border-radius: 30px;
    // box-shadow: 2px 2px 10px #dadada;
    // cursor: pointer;

    .e-player-title {
        position: absolute;
        top: -270px;
        left: 20px;
        font-size: 18px;
        // font-weight: bold;
        color: #979797;
        cursor: default;
    }

    .e-player-3:nth-child(2) {
        top: -230px;
        left: 115px;
    }

    .e-player-3:nth-child(3) {
        top: -130px;
        left: 35px;
    }

    .e-player-3:nth-child(4) {
        top: -130px;
        left: 195px;
    }

    .e-player-3 {
        position: absolute;
        width: 110px;
        height: 110px;
        top: -200px;
        cursor: pointer;
        display: flex;
        // 纵向排列
        flex-direction: column;
        // 居中对齐
        align-items: center;

        .e-player-3-head {
            position: absolute;
            top: 5px;
            width: 85px;
            height: 85px;
            border-radius: 50%;
            overflow: hidden;
            box-shadow: 5px 5px 30px #b3b3b3;

            .e-player-3-head-img {
                position: absolute;
                left: 5px;
                width: 75px;
                height: 75px;
                border-radius: 50%;
                top: 5px;
            }
        }

        .e-player-3-head:hover {
            box-shadow: 5px 5px 30px #868686;
        }

        .top-1 {
            background: linear-gradient(#fde985, #d7ad17);
        }

        .top-2 {
            background: linear-gradient(#fcfcfc, #b2b2b2);
        }

        .top-3 {
            background: linear-gradient(#facda1, #d98651);
        }

        .e-player-3-name {
            position: absolute;
            bottom: 0;
            width: auto;
            height: 20px;
            line-height: 20px;
            font-size: 14px;
            background-color: #ffffff;
            box-shadow: 2px 2px 10px #e9e9e9;
            border-radius: 5px;
            color: #ffa116;
        }
    }

    .e-player-after {
        position: absolute;
        width: 350px;
        height: 530px;
        border-radius: 30px;
        box-shadow: 2px 2px 10px #dadada;
        cursor: pointer;
        overflow: hidden;

        .e-player-after-item {
            position: relative;
            height: 57px;
            width: 100%;

            .e-player-after-item-inner {
                position: relative;
                height: 57px;
                width: 95%;
                left: 2.5%;
                border-bottom: 1px solid #e7e7e781;
                display: flex;
                color: #535353;

                .e-player-after-item-inner-number {
                    position: absolute;
                    width: 20px;
                    height: 57px;
                    line-height: 57px;
                    left: 10px;
                    font-size: 14px;
                    text-align: center;
                }

                .e-player-after-item-inner-head {
                    position: absolute;
                    width: 30px;
                    height: 30px;
                    left: 40px;
                    top: 15px;
                    border-radius: 50%;
                    overflow: hidden;

                    .e-player-after-item-inner-head-img {
                        width: 30px;
                        height: 30px;
                    }
                }

                .e-player-after-item-inner-username {
                    position: absolute;
                    left: 80px;
                    top: 14px;
                    height: 15px;
                    line-height: 15px;
                    width: 200px;
                    font-size: 12px;
                    overflow: hidden;
                    color: #ffa116;
                }

                .e-player-after-item-inner-contests-num {
                    position: absolute;
                    left: 80px;
                    top: 30px;
                    height: 15px;
                    line-height: 15px;
                    width: 200px;
                    font-size: 12px;
                    overflow: hidden;
                    color: #777777;
                }
            }
        }

        .e-player-after-item:hover {
            background-color: #f1f7f7;
        }
    }

    .tips {
        position: absolute;
        bottom: 0px;
        height: 73px;
        line-height: 73px;
        font-size: 15px;
        width: 100%;
        text-align: center;
        color: #0088cc;
        cursor: pointer;
    }

    .tips:hover {
        background-color: #f1f7f7;

    }

    .e-player-after:hover {
        box-shadow: 2px 2px 20px #c5c5c5;
    }
}
</style>