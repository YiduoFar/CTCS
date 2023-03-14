<template>
    <div class="article-details">
        <Nav></Nav>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
            <el-breadcrumb-item v-if="$route.query.type == 'news'">新闻</el-breadcrumb-item>
            <el-breadcrumb-item v-if="$route.query.type == 'announcement'">公告</el-breadcrumb-item>
            <el-breadcrumb-item>{{ article.title }}</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 新闻或公告 -->
        <div class="article " ref="article">
            <!-- 题目 -->
            <div class="art-title ">{{ article.title }}</div>
            <!-- bar -->
            <div class="bar1 ">
                <!-- 时间 -->
                <div class="art-time">{{ $moment(article.publishTime).format('YYYY.MM.DD HH:mm') }}</div>
            </div>
            <!-- line -->
            <div class="line"></div>
            <!-- 内容 -->
            <div class="content" v-html="article.content"></div>
            <!-- line -->
            <div class="line2"></div>
        </div>
        <!-- 回到顶部 -->
        <div class="fit-to-top el-icon-arrow-up" @click="toTop()">
        </div>
        <Foot class="art-foot"></Foot>
    </div>
</template>

<script>
import Nav from '@/components/Nav.vue';
import Foot from '@/components/Foot.vue';
import emoji from '../components/backstage/emoji.vue';
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
export default {
    data: () => ({
        /* 新闻或公告Vo */
        article: {
            title: '',
            content: '',
            publishTime: '',
        },
    }),
    methods: {
        ...mapActions(['selectNewsById']),
        ...mapActions(['selectAnnouncementById']),
        /* 初始化数据 */
        init() {
            if (this.$route.query.type == 'news') {
                this.doGetNews(this.$route.query.id)
            }
            if (this.$route.query.type == 'announcement') {
                this.doGetAnnouncement(this.$route.query.id)
            }
        },
        /* 得到新闻 */
        async doGetNews(newsId) {
            const { code, message, data } = await this.selectNewsById(newsId);
            if (code === 200) {
                this.article.title = data.newsTitle;
                this.article.content = data.newsContent;
                this.article.publishTime = data.newsPublishTime;
            }
        },
        /* 得到公告 */
        async doGetAnnouncement(announcementId) {
            const { code, message, data } = await this.selectAnnouncementById(announcementId);
            if (code === 200) {
                this.article.title = data.announcementTitle;
                this.article.content = data.announcementContent;
                this.article.publishTime = data.announcementPublishTime;
            }
        },
        /* 到顶部 */
        async toTop() {
            document.getElementsByClassName('article-details')[0].scrollTo({ top: 0, behavior: "smooth" });
        }
    },
    created() {
        this.init();
    },
    components: {
        Nav, Foot
    }
}
</script>


<style lang="less" scoped>
.article-details {
    width: 100%;
    height: 100%;
    background-color: #f6f7f9;
    overflow-x: hidden;
    overflow-y: scroll;

    .bread {
        position: relative;
        left: 530px;
        font-size: 12px;
    }

    .article {
        position: relative;
        width: 48%;
        left: 26%;
        top: 100px;
        min-height: 500px;
        background-color: #fff;
        padding-bottom: 80px;
        box-shadow: 0 0 10px #f6f7f9;

        .art-title {
            position: relative;
            width: 70%;
            left: 15%;
            font-size: 28px;
            color: #222;
            font-weight: bold;
            padding-top: 25px;
            padding-bottom: 20px;

        }

        .bar1 {
            position: relative;
            left: 15%;
            display: flex;
            color: #999;
            text-align: center;
            // padding-bottom: 20px;
            width: 70%;
            margin-bottom: 20px;

            .art-time {
                position: relative;
                font-size: 12px;
                margin-right: 20px;
            }

        }

        
        .line {
            position: relative;
            border-top: 1px solid #e9e9e9;
            width: 80%;
            left: 10%;
            padding-bottom: 40px;
        }

        .content {
            position: relative;
            width: 70%;
            left: 15%;
            min-height: 450px;

            ::v-deep img {
                // 保持原图的宽高比

                // object-fit: cover;

                // object-fit: scale-down;

                width: auto;
                height: auto;
                max-width: 100%;
                max-height: 100%;
            }

        }


        .line2 {
            position: relative;
            border-bottom: 1px solid #e9e9e9;
            width: 80%;
            left: 10%;
            padding-top: 40px;
        }
    }



    .fit-to-top {
        position: fixed;
        bottom: 120px;
        right: 420px;
        width: 55px;
        height: 55px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px #e6e6e68f;
        text-align: center;
        line-height: 55px;
        font-size: 20px;
        cursor: pointer;
    }

    .art-foot {
        position: relative;
        top: 150px;
    }
}
</style>