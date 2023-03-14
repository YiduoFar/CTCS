<template>
    <div class="backstage">
        <Nav></Nav>
        <div class="backstage-body ">
            <!-- 菜单 -->
            <el-menu :default-active="$route.query.focusIndex" class="el-menu-vertical-demo" :default-openeds="openeds"
                background-color="#fff" text-color="#000" active-text-color="#0e8f48">
                <!-- 个人信息 -->
                <el-menu-item index="1" id="1" @click="toFocusView(1)">
                    <i class="el-icon-user"></i>
                    <span slot="title">个人信息</span>
                </el-menu-item>
                <!-- 竞赛 -->
                <el-submenu index="1.">
                    <template slot="title">
                        <i class="el-icon-cpu"></i>
                        <span>竞赛</span>
                    </template>
                    <el-menu-item-group>
                        <!-- 我的竞赛 -->
                        <el-menu-item index="2" id="2" @click="toFocusView(2)">我的竞赛</el-menu-item>
                        <!-- 发布竞赛 -->
                        <el-menu-item index="3" id="3" @click="toFocusView(3)" v-if="show1">发布竞赛</el-menu-item>
                        <!-- 竞赛管理 -->
                        <el-menu-item index="4" id="4" @click="toFocusView(4)" v-if="show1">竞赛管理</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <!-- 活动 -->
                <el-submenu index="2.">
                    <template slot="title">
                        <i class="el-icon-s-promotion"></i>
                        <span>活动</span>
                    </template>
                    <el-menu-item-group>
                        <!-- 我的活动 -->
                        <el-menu-item index="5" id="5" @click="toFocusView(5)">我的活动</el-menu-item>
                        <!-- 创建活动 -->
                        <el-menu-item index="6" id="6" @click="toFocusView(6)">创建活动</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <!-- 队伍 -->
                <el-submenu index="3.">
                    <template slot="title">
                        <i class="el-icon-ship"></i>
                        <span>队伍</span>
                    </template>
                    <el-menu-item-group>
                        <!-- 我的队伍 -->
                        <el-menu-item index="7" id="7" @click="toFocusView(7)">我的队伍</el-menu-item>
                        <!-- 创建队伍 -->
                        <el-menu-item index="8" id="8" @click="toFocusView(8)">创建队伍</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <!-- 帖子 -->
                <el-submenu index="4.">
                    <template slot="title">
                        <i class="el-icon-copy-document"></i>
                        <span>帖子</span>
                    </template>
                    <el-menu-item-group>
                        <!-- 我的帖子 -->
                        <el-menu-item index="9" id="9" @click="toFocusView(9)">我的帖子</el-menu-item>
                        <!-- 发布帖子 -->
                        <!-- <el-menu-item index="10" id="10" @click="toFocusView(10)">发布帖子</el-menu-item> -->
                    </el-menu-item-group>
                </el-submenu>
                <!-- 系统管理 -->
                <el-menu-item index="10" v-if="show2" id="10"  @click="toFocusView(10)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">系统管理</span>
                </el-menu-item>
            </el-menu>
            <!-- 个人信息 -->
            <UserEdit v-if="focusView == 1"></UserEdit>
            <!-- 我的竞赛 -->
            <CompetitionMine v-if="focusView == 2"></CompetitionMine>
            <!-- 发布竞赛 -->
            <CompetitionAdd v-if="focusView == 3"></CompetitionAdd>
            <!-- 管理竞赛 -->
            <CompetitionManage v-if="focusView == 4"></CompetitionManage>
            <!-- 我的活动 -->
            <ActivityMine v-if="focusView == 5"></ActivityMine>
            <!-- 发布活动 -->
            <ActivityAdd v-if="focusView == 6"></ActivityAdd>
            <!-- 我的队伍 -->
            <TeamMine v-if="focusView == 7"></TeamMine>
            <!-- 创建队伍 -->
            <TeamAdd v-if="focusView == 8"></TeamAdd>
            <!-- 我的帖子 -->
            <ArticleMine v-if="focusView == 9"></ArticleMine>
            <!-- 创建帖子 -->
            <!-- <ArticleAdd v-if="focusView == 10"></ArticleAdd> -->
            <!-- 系统管理 -->
            <SystemManage v-if="focusView == 10"></SystemManage>
        </div>
        <Foot class="back-foot"></Foot>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import UserEdit from '../components/backstage/UserEdit.vue'
import CompetitionMine from '../components/backstage/CompetitionMine.vue'
import CompetitionAdd from '@/components/backstage/CompetitionAdd.vue'
import CompetitionManage from '@/components/backstage/CompetitionManage.vue'
import ActivityMine from '@/components/backstage/ActivityMine.vue'
import ActivityAdd from '@/components/backstage/ActivityAdd.vue'
import TeamMine from '@/components/backstage/TeamMine.vue'
import TeamAdd from '@/components/backstage/TeamAdd.vue'
import ArticleMine from '@/components/backstage/ArticleMine.vue'
// import ArticleAdd from '@/components/backstage/ArticleAdd.vue'
import SystemManage from '@/components/backstage/SystemManage.vue'
export default {
    data: () => ({
        /**
         * 控制变量
         * 进入页面默认全部子菜单为打开状态
         */
        openeds: ['1.', '2.', '3.', '4.'],
        /**
         * 控制变量
         * 当前focus的子组件的隐藏显示
         */
        focusView: 1,
        /** 
         * 控制变量
         * 在竞赛的编辑子组件时，控制离开前的询问确认
         */
        eStatus: true,
        /**
         * 控制变量2
         * 在活动的编辑子组件时，控制离开前的询问确认
         */
        eStatus2: true,
        /**
         * 控制变量3
         * 在队伍的编辑子组件时，控制离开前的询问确认
         */
        eStatus3: true
    }),
    methods: {
        /**
         * 前往功能子组件
         * @param {*} index 
         */
        toFocusView(index) {
            // 若在竞赛的编辑子组件时，离开前询问确认
            if (this.focusView == 4 && !this.eStatus && index != 4) {
                this.$confirm('确定离开编辑？, 该页面数据将不会保留', '提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    this.$router.push({ query: { focusIndex: index } })
                    this.focusView = index
                    this.eStatus = true
                }).catch(() => {
                    return
                });
            } else if (this.focusView == 5 && !this.eStatus2 && index != 5) {
                this.$confirm('确定离开编辑？, 该页面数据将不会保留', '提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    this.$router.push({ query: { focusIndex: index } })
                    this.focusView = index
                    this.eStatus2 = true
                }).catch(() => {
                    return
                });
            } else if (this.focusView == 7 && !this.eStatus3 && index != 7) {
                this.$confirm('确定离开编辑？, 该页面数据将不会保留', '提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    this.$router.push({ query: { focusIndex: index } })
                    this.focusView = index
                    this.eStatus3 = true
                }).catch(() => {
                    return
                });
            } else {
                // 其他情况不询问，更改query为了刷新时页面不会重新回到个人信息
                this.$router.push({ query: { focusIndex: index } })
                this.focusView = index
            }
        },
        /**
         * 在竞赛的编辑子组件时，离开前询问确认的控制变量赋值函数
         */
        editStatus(status) {
            this.eStatus = status
        },
        /**
         * 在活动的编辑子组件时，离开前询问确认的控制变量赋值函数2
         */
        editStatus2(status) {
            this.eStatus2 = status
        },
        /**
         * 在队伍的编辑子组件时，离开前询问确认的控制变量赋值函数2
         */
        editStatus3(status) {
            this.eStatus3 = status
        }
    },
    computed: {
        /**
         * 控制发布竞赛、竞赛管理的隐藏与显示
         */
        show1() {
            let identity = JSON.parse(localStorage.getItem('identity'))
            return identity == 0 ? false : true;
        },
        /**
         * 控制系统管理隐藏与显示
         */
        show2() {
            let identity = JSON.parse(localStorage.getItem('identity'))
            return identity == 2 ? true : false;
        }
    },
    mounted() {
        /**
         * 页面加载时，获取上个页面带过来的query，实现菜单子组件的选择
         */
        document.getElementById(this.$route.query.focusIndex).click();
    },
    components: {
        Nav,
        Foot,
        UserEdit,
        CompetitionMine,
        CompetitionAdd,
        CompetitionManage,
        ActivityMine,
        ActivityAdd,
        TeamMine,
        TeamAdd,
        // ArticleAdd,
        ArticleMine,
        SystemManage
    },
}
</script>

<style lang="less" scoped>
.backstage {
    width: 100%;
    height: 100%;
    // display: flex;
    overflow-y: scroll;


    .backstage-body {
        position: relative;
        width: 100%;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;
        background-color: #ffffff;
        min-height: 100vh;
        padding-bottom: 100px;
        // height: auto;


        .el-menu-vertical-demo {
            position: absolute;
            top: 80px;
            left: 350px;
            width: 200px;
            // height: 850px;
            box-shadow: 0px 0px 10px #4d4d4d2c;
        }

        .back-foot {
            position: relative;
            margin-top: 500px;
            background-color: #ffffff48;
        }
    }

}

::v-deep .el-menu {
    border: none;
}
</style>