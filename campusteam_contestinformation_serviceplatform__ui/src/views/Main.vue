<template>
    <div class="main " id="main">
        <!-- 导航栏 -->
        <Nav></Nav>
        <!-- body -->
        <div class="main-body " id="main-body">
            <!-- 日历 -->
            <Calendar class="main-calendar"></Calendar>
            <!-- 轮播图 -->
            <Slideshow class="main-slideshow"></Slideshow>
            <!-- 优秀队伍 -->
            <ExcellentTeam class="main-excellent-team"></ExcellentTeam>
            <!-- 优秀个人 -->
            <ExcellentPlayer class="main-excellent-player"></ExcellentPlayer>
            <!-- 新闻 -->
            <News class="main-news"></News>
            <!-- 公告 -->
            <Announcement class="main-announcement"></Announcement>
            <!-- 到顶部 -->
            <ToTop @click.native="toTop"></ToTop>
            <!-- foot -->
            <Foot class="main-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Calendar from '../components/hall/Calendar.vue'
import Slideshow from '../components/hall/Slideshow.vue'
import ExcellentTeam from '../components/hall/ExcellentTeam.vue'
import ExcellentPlayer from '@/components/hall/ExcellentPlayer.vue'
import News from '@/components/hall/News.vue'
import Announcement from '../components/hall/Announcement.vue'
import ToTop from '@/components/hall/ToTop.vue'
import Foot from '@/components/Foot.vue'
export default {
    components: {
        Nav,
        Calendar,
        Slideshow,
        ExcellentTeam,
        ExcellentPlayer,
        News,
        Announcement,
        ToTop,
        Foot
    },
    methods: {
        /**
         * 去登录
         */
        toLoginPage() {
            this.$router.push('/login')
        },
        /**
         * 回到顶部
         */
        toTop() {
            document.getElementById("main-body").scrollTo({ top: 0, behavior: "smooth" });
        }
    }
}
</script>

<style lang="less">
.main {
    width: 100%;
    height: auto;

    .main-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;

        .main-calendar {
            position: absolute;
            top: 100px;
            left: 1310px;
        }

        .main-slideshow {
            position: absolute;
            top: 70px;
            left: 260px;
        }

        .main-excellent-team {
            left: 260px;
            top: 580px;
        }

        .main-excellent-player {
            left: 920px;
            top: 815px;
        }

        .main-news {
            top: 580px;
            left: 1330px;
        }

        .main-announcement {
            top: 1050px;
            left: 1330px;
        }

        .main-foot {
            top: 1450px;
        }
    }
}
</style>