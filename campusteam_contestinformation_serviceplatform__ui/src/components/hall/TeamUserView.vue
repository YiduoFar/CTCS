<template>
    <!-- user -->
    <div class="model-user ">
        <!-- 头像 -->
        <img :src="userHead" alt="" class="user-head">
        <!-- 昵称 -->
        <div class="user-name">{{ userName }}</div>
        <!-- 最近加入text -->
        <div class="user-recently-join-text">最近加入的队伍 </div>
        <!-- 查看更多 -->
        <div class="look-more" v-if="teamRecentlyJoinedList.length >= 3" @click="$router.push({ path: '/backstage', query: { focusIndex: 7 } })">查看全部...</div>
        <div class="user-recently-join-teamlist ">
            <!-- 无数据 -->
            <el-empty description="您未加入任何队伍" :image-size="80" style="font-size: 12px;top: -20px;position:relative;"
                v-if="teamRecentlyJoinedList.length == 0"></el-empty>
            <!-- 有数据 -->
            <div class="user-recently-join-teamlist-item ts02s" v-for="item in teamRecentlyJoinedList"
                @click="$router.push({ path: '/team_details', query: { teamId: item.teamId } })">
                <img class="item-team-logo" :src="item.teamLogo" alt="">
                <div class="item-team-name ">{{ item.teamName }}</div>
                <div class="item-team-captain ">{{ item.teamCaptionUserName }}</div>
            </div>
        </div>
        <!-- 创建组队 -->
        <div class="create-team-btn ts05s"  @click="$router.push({ path: '/backstage', query: { focusIndex: 8 } })"><i class="el-icon-plus"></i> 创建新队伍</div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        teamRecentlyJoinedList: []
    }),
    methods: {
        ...mapActions('team', ['selectTeamRencentlyJoinedByUserId']),
        async init() {
            let userId = localStorage.getItem('userId');
            const { code, message, data } = await this.selectTeamRencentlyJoinedByUserId(userId);
            if (code === 200) {
                this.teamRecentlyJoinedList = [...data];
            }
        }
    },
    created() {
        this.init();
    },
    computed:{
        userName(){
            return localStorage.getItem('userName');
        },
        userHead(){
            return localStorage.getItem('userHead');
        },
    }
}
</script>

<style lang="less" scoped>
.model-user {
    position: absolute;
    width: 24.5%;
    right: 0;
    height: 100%;
    background-color: #ffffff9f;

    .user-head {
        position: absolute;
        width: 60px;
        height: 60px;
        border-radius: 50%;
        top: 25px;
        left: 30px;
    }

    .user-name {
        position: absolute;
        width: 100px;
        height: 60px;
        font-size: 18px;
        left: 100px;
        top: 25px;
        height: 60px;
        line-height: 60px;
        text-align: center;
    }

    .user-recently-join-text {
        position: absolute;
        width: 120px;
        height: 20px;
        line-height: 20px;
        text-align: center;
        top: 100px;
        left: 15px;
        font-size: 14px;
        color: #a59e9e;
    }

    .look-more {
        position: absolute;
        width: 70px;
        height: 20px;
        line-height: 20px;
        text-align: center;
        top: 100px;
        right: 15px;
        font-size: 14px;
        cursor: pointer;
        color: #277ccc;
    }

    .user-recently-join-teamlist {
        position: absolute;
        width: 100%;
        height: 190px;
        overflow: scroll;
        top: 125px;

        .user-recently-join-teamlist-item {
            position: relative;
            width: 90%;
            left: 5%;
            height: 45px;
            border: 1px solid #dddddd;
            background-color: #fafafa83;
            margin-bottom: 10px;
            color: #8b8b8b;
            cursor: pointer;

            .item-team-logo {
                position: absolute;
                width: 30px;
                height: 30px;
                left: 142px;
                top: 7px;
            }

            .item-team-name {
                position: absolute;
                width: 130px;
                text-align: right;
                height: 100%;
                line-height: 45px;
                font-size: 14px;
                left: 0px;
            }

            .item-team-captain {
                position: absolute;
                width: 130px;
                text-align: left;
                height: 100%;
                line-height: 45px;
                right: 0px;
                font-size: 14px;
            }
        }

        .user-recently-join-teamlist-item:hover {
            box-shadow: 0 0 10px #cccccca6;
        }
    }

    .create-team-btn {
        position: absolute;
        width: 120px;
        height: 25px;
        bottom: 10px;
        right: 20px;
        border-radius: 15px;
        background-color: #0eb833;
        cursor: pointer;
        font-size: 14px;
        text-align: center;
        line-height: 25px;
        color: #e7e7e7;
    }

    .create-team-btn:hover {
        background-color: #15c73c;
        color: #ffffff;

    }
}
</style>