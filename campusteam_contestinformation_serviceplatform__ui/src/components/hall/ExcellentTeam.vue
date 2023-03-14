<template>
    <div class="e-team ">
        <!-- title -->
        <div class="e-team-title">优秀队伍</div>
        <!-- 线 -->
        <div class="separator-op"></div>
        <!-- 表头 -->
        <div class="e-team-header ">
            <div class="e-team-header-item" v-for="item in teamItems">
                {{ item }}
            </div>
        </div>
        <!-- 表项 -->
        <div class="e-team-item " v-for="item in excellentTeam">
            <div class="e-team-item-logo">
                <img class="e-team-item-logo-img" src="../../assets/img/04.png" alt="">
            </div>
            <div class="e-team-item-info ts02s">{{ item.team.teamName }}</div>
            <div class="e-team-item-info ts02s">{{ item.team.teamCaptionUserName }}</div>
            <div class="e-team-item-info ts02s">{{ item.team.teamMemberNum }}/{{ item.team.teamMemberNumMax }}</div>
            <div class="e-team-item-info ts02s" v-if="item.team.teamStatus == 0" style="color: gray;">{{ status[0] }}</div>
            <div class="e-team-item-info ts02s" v-if="item.team.teamStatus == 1" style="color: #f75a5a;">{{ status[1] }}
            </div>
            <div class="e-team-item-info ts02s">{{ item.num }}场</div>
        </div>
        <div class="separator-op3"></div>
        <!-- 分页
        <div class="e-team-page ">
            <el-pagination layout="prev, pager, next"  :total="30" class="page-el-pagination" @current-change="currentChange">
            </el-pagination>
        </div> -->
        <div class="separator-op2"></div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        teamItems: ['队伍名', '队长', '人数', '状态', '参赛数'],
        excellentTeam: [],
        status: ['不招新', '招新中']
    }),
    methods: {
        ...mapActions('count', ['getExcellentTeam']),
        async init() {
            const { code, message, data } = await this.getExcellentTeam();
            if (code === 200) {
                this.excellentTeam = [...data]
            }
        }
    },
    created() {
        this.init()
    }
}
</script>

<style lang="less" scoped>
.e-team {
    width: 590px;
    height: 750px;
    position: absolute;
    // left: 300px;
    // top: 580px;
    // color: #f75a5a;

    .e-team-title {
        position: absolute;
        top: -35px;
        left: 20px;
        font-size: 18px;
        // font-weight: bold;
        color: #979797;
        cursor: default;
    }

    .e-team-header {
        width: 100%;
        height: 45px;
        display: flex;
        /* 均匀分布 */
        justify-content: space-around;
        font-size: 15px;
        font-weight: bold;
        color: #979797;
        line-height: 45px;


        .e-team-header-item:nth-child(1) {
            width: 100px;
            text-align: center;
        }
    }

    .e-team-item {
        width: 100%;
        height: 35px;
        position: relative;
        display: flex;
        /* 均匀分布 */
        justify-content: center;
        line-height: 35px;
        font-size: 13px;
        font-weight: bold;
        color: #666666;
        cursor: pointer;

        .e-team-item-logo {
            position: absolute;
            width: 20px;
            height: 20px;
            overflow: hidden;
            left: 15px;
            top: 5px;

            .e-team-item-logo-img {
                width: 20px;
                height: 20px;

            }
        }

        .e-team-item-info {
            position: absolute;
            text-align: center;
            // overflow: hidden;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .e-team-item-info:nth-child(2) {
            width: 100px;
            left: 40px;
            color: #ffa116;
            font-weight: lighter;
        }

        .e-team-item-info:nth-child(3) {
            width: 90px;
            left: 177px;
            // color: #57b366;
            font-weight: lighter;
        }

        .e-team-item-info:nth-child(4) {
            width: 50px;
            left: 300px;
            color: #949494;

        }

        .e-team-item-info:nth-child(5) {
            width: 50px;
            left: 400px;
            color: #d87069;
        }

        .e-team-item-info:nth-child(6) {
            width: 70px;
            left: 498px;
        }
    }


    .e-team-item:nth-child(2n) {
        background-color: #fafafa;
    }

    .e-team-item:nth-child(2n+1) {
        background-color: #fff;
    }

    .e-team-item:hover {
        background-color: #ebeeeb;
    }

    .e-team-page {
        position: relative;
        bottom: 0;
        left: 380px;
        height: 40px;
        width: 190px;

        .page-el-pagination {
            position: absolute;
            top: 5px;
        }
    }
}
</style>