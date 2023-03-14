<template>
    <!-- 队伍星级 -->
    <div class="team-stars">
        <!-- title -->
        <div class="team-stars-title">星级</div>
        <!-- tips -->
        <el-popover placement="top-start" width="120" trigger="hover" :content="tip"
            class="title-tips-2">
            <i slot="reference" class="el-icon-info"></i>
        </el-popover>
        <!-- list -->
        <div class="team-stars-list ">
            <!-- item -->
            <div class="team-stars-item " v-for="item in teamStarList">
                <!-- team-name -->
                <div class="team-stars-item-name">{{ item.teamName }}</div>
                <el-rate class="team-stars-item-stars " v-model="item.teamStar" disabled show-score text-color="#ff9900"
                    score-template="{value}">
                </el-rate>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default{
    data:()=>({
        teamStarList: [],
        tip:'根据队伍星级排序'
    }),
    methods:{
        ...mapActions('team', ['selectTeamStar']),
        async init(){
            const {code, message, data} = await this.selectTeamStar();
            if (code === 200) {
                this.teamStarList = [...data];
            }
        }
    },
    created(){
        this.init();
    }
}
</script>

<style lang="less" scoped>
.team-stars {
    position: absolute;
    width: 18.1%;
    height: 820px;
    overflow: hidden;
    top: 455px;
    right: 13%;
    background-color: #ffffff9f;
    // border-radius: 10px;

    .team-stars-title {
        position: absolute;
        top: 10px;
        left: 25px;
        font-size: 20px;
        font-weight: bold;
        color: #2b2b2ba6;
        // font-style: italic;
    }

    .title-tips-2 {
        position: absolute;
        top: 14px;
        left: 70px;
        width: 20px;
        height: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        font-size: 18px;
        color: #a59e9e;
    }

    .team-stars-list {
        position: absolute;
        width: 100%;
        height: 740px;
        top: 60px;

        .team-stars-item {
            position: relative;
            width: 100%;
            height: 50px;
            line-height: 50px;

            .team-stars-item-name {
                position: absolute;
                left: 25px;
                font-size: 14px;
            }

            .team-stars-item-stars {
                position: absolute;
                left: 185px;
                top: 14px;
                font-size: 14px;
            }
        }
    }
}

</style>